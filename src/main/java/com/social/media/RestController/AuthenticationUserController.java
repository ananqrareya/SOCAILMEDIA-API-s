package com.social.media.RestController;

import com.social.media.Dto.LoginDto;
import com.social.media.Dto.UserDto;
import com.social.media.Entity.User;
import com.social.media.ExceptionHandler.RegisterUserExceptions;
import com.social.media.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/user")
public class AuthenticationUserController {
@Autowired
private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;


            @PostMapping("/register")
        public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
                try {
                    User user = userService.newUser(userDto);
                    return ResponseEntity.ok().body(user);
                }catch (RegisterUserExceptions e){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error registering user: " + e.getMessage());

                }
                }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto ,HttpSession session) {
     Authentication existingAuth=SecurityContextHolder.getContext().getAuthentication();
     if(existingAuth!=null&&existingAuth.isAuthenticated()&&!(existingAuth instanceof AnonymousAuthenticationToken)){
         return new ResponseEntity<>("User is already logged in!", HttpStatus.CONFLICT);
     }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPass())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return new ResponseEntity<>("User login successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>("User logged out successfully!", HttpStatus.OK);
    }


}
