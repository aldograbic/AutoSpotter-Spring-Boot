package com.example.AutoSpotter.classes.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.example.AutoSpotter.config.AuthenticationType;
import com.example.AutoSpotter.config.EmailNotVerifiedException;

@Service
public class CustomUserDetailsService extends SavedRequestAwareAuthenticationSuccessHandler implements UserDetailsService  {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateAuthenticationType(String username, String oauth2ClientName) {
        AuthenticationType authType = AuthenticationType.valueOf(oauth2ClientName.toUpperCase());
        userRepository.updateAuthenticationType(username, authType);
    }

    public void processOAuthPostLogin(String username, String email, String firstName, String lastName, String profileImage) {
        User existUser = userRepository.findByUsername(username);
         
        if (existUser == null) {
            User newUser = new User();

            int atIndex = email.indexOf('@');
            if (atIndex >= 0) {
                String extractedUsername = email.substring(0, atIndex);
                newUser.setDisplayUsername(extractedUsername);
            } else {
                newUser.setDisplayUsername(username);
            }

            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setProfileImage(profileImage);
            newUser.setEmailVerified(true);

            userRepository.saveOAuth2(newUser);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        if (!user.isEmailVerified()) {
            throw new EmailNotVerifiedException("Email not verified");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        String roleName = "USER";

        if (roleName != null && !roleName.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(roleName));
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                authorities
        );
    }
}