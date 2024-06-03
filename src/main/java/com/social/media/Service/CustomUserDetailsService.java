    package com.social.media.Service;

    import com.social.media.Dao.UserRepository;
    import com.social.media.Entity.User;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    import java.util.List;
    @Service

    public class CustomUserDetailsService implements UserDetailsService {
        private final UserRepository userRepository;

        public CustomUserDetailsService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));


            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPasswords(),
                    authorities
            );
        }
    }
