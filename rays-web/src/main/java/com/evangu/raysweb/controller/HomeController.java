package com.evangu.raysweb.controller;

import com.evangu.raysweb.config.RaysUsernamePasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.protocol.http.AuthenticationHeader;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author: Gu danpeng
 * @data: 2018-9-18
 * @version：1.0
 */
@Controller
public class HomeController {

    private RestTemplate restTemplate;

    @Autowired
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        return this.restTemplate;
    }

    @RequestMapping("/home")
    public ModelAndView index(@RequestParam(value = "access_token", required = false) String accessToken) {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();

        // 当事人
        Object principal = auth.getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println(username);
        ModelAndView m = new ModelAndView("home");
        m.addObject("message",username);
        m.addObject("authorities",auth.getAuthorities());
        m.addObject("details",auth.getDetails());
        m.addObject("credentials",auth.getCredentials());

        System.out.println(auth.getDetails());
        System.out.println(auth.getCredentials());


        OAuth2Authentication oAuth2Authentication;
        OAuth2AuthenticationDetails oauthDetails;

        if (auth instanceof OAuth2Authentication) {
            oAuth2Authentication = (OAuth2Authentication) auth;
            Object details = oAuth2Authentication.getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                oauthDetails = (OAuth2AuthenticationDetails) details;
                String resourceOrder = getOrder(oauthDetails);
                m.addObject("resourceOrder",resourceOrder);
            }
        } else {
            throw new IllegalStateException("Authentication not supported!");
        }

        return m;
    }

    public String getOrder(OAuth2AuthenticationDetails oauthDetails){
        String urlTemplate = "http://localhost:8082/api/order/1?access_token={0}";
        String url = MessageFormat.format(urlTemplate,oauthDetails.getTokenValue());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,String.class);

        System.out.println(oauthDetails.getTokenValue());
        return  responseEntity.getBody();
    }
}
