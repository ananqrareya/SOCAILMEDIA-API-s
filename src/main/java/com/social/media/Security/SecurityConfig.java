    package com.social.media.Security;

    import com.social.media.Entity.User;
    import com.social.media.Service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    @EnableMethodSecurity
    public class SecurityConfig {
      private UserService userService ;
      public  SecurityConfig(UserService userService1){
          this.userService=userService1;
      }
      @Bean
        public static PasswordEncoder passwordEncoder(){
          return new BCryptPasswordEncoder();
      }
      @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
          return configuration.getAuthenticationManager();
      }
    @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
          http.csrf(csrf->csrf.disable())
                  .authorizeHttpRequests(authorize->
                          authorize.requestMatchers(HttpMethod.POST,"/api/user/**").permitAll()
                                  .requestMatchers("/api/user/register").permitAll()
                                  .requestMatchers(HttpMethod.GET,"api/socail/**").hasAuthority("USER")
                                  .requestMatchers("api/socail/**").hasAuthority("USER")

                                  .anyRequest().authenticated()

                          );
           http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        );
           http .logout(logout-> logout
                   .logoutUrl("/logout")
                   .logoutSuccessUrl("/login?logout")
                   .invalidateHttpSession(true)
                   .deleteCookies("JSESSIONID")
                   .permitAll());



          return http.build();
    }


    }
