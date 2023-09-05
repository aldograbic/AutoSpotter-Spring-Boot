package com.example.AutoSpotter.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.AutoSpotter.classes.user.CustomUserDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired CustomUserDetailsService userService;
     
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        String oauth2ClientName = oauth2User.getOauth2ClientName();
        String username = oauth2User.getEmail();
        String email = oauth2User.getEmail();

        Map<String, Object> attributes = oauth2User.getAttributes();

        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");
        String phoneNumber = (String) attributes.get("phone_number");
        String address = (String) attributes.get("address");
    

        userService.processOAuthPostLogin(username, email, firstName, lastName, phoneNumber, address);

        userService.updateAuthenticationType(username, oauth2ClientName);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}