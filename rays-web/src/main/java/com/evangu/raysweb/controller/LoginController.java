package com.evangu.raysweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: Gu danpeng
 * @data: 2018-9-18
 * @version：1.0
 */
@Controller
public class LoginController {

    @Autowired
    OAuth2RestTemplate restTemplate;

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("login");
    }

    @RequestMapping("/login")
    public void login(@RequestHeader Object  requestHeader) {
        restTemplate.getAccessToken();
    }
}
