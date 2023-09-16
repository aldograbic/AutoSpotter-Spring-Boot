package com.example.AutoSpotter.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.example.AutoSpotter.classes.user.CustomUserDetailsService;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    CustomUserDetailsService userService;

    @Autowired
    UserRepository userRepository;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        
        UserDetails authenticatedUserDetails = (UserDetails) authentication.getPrincipal();
        String username = authenticatedUserDetails.getUsername();
        User user = userRepository.findByUsername(username);

        if(user.getAuthType() == null) {
            userService.updateAuthenticationType(username, "database");
            response.sendRedirect("/?uspjesnaPrijava");
        } else {
            response.sendRedirect("/?uspjesnaPrijava");
        }
    }
}