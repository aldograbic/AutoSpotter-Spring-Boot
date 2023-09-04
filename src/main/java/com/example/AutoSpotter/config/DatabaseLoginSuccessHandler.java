package com.example.AutoSpotter.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.example.AutoSpotter.classes.user.CustomUserDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired CustomUserDetailsService userService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
                //nismo sigurni za ovu prvu liniju ispod
        UserDetails authenticatedUserDetails = (UserDetails) authentication.getPrincipal();
        userService.updateAuthenticationType(authenticatedUserDetails.getUsername(), "database");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
