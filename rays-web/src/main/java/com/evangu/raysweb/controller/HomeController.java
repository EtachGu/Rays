package com.evangu.raysweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.protocol.http.AuthenticationHeader;

/**
 * @author: Gu danpeng
 * @data: 2018-9-18
 * @version：1.0
 */
@Controller
public class HomeController {

    @RequestMapping("/home")
    public ModelAndView index(@RequestParam(value = "access_token", required = false) String accessToken) {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();

        String userName = (String) auth.getPrincipal();
        System.out.println(userName);
        ModelAndView m = new ModelAndView("home");
        m.addObject("message",userName);
        m.addObject("authorities",auth.getAuthorities());
        m.addObject("details",auth.getDetails());
        m.addObject("credentials",auth.getCredentials());

        System.out.println(auth.getDetails());
        System.out.println(auth.getCredentials());
        return m;
    }
}
