package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.User;
import com.scm.entities.providers;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepo;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    Logger logger=LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub
        logger.info("OAuthAuthenticationSuccessHandler");
        //new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");


        //identify the provider
        var OAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
        String authorizedClientRegistrationId=OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);

        var oauthUser=(DefaultOAuth2User)authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key,value)->{
            logger.info(key+":"+value);
        });

        User user=new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);


        if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());;
        }
        else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){


        }
        
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
