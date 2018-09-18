package com.evangu.raysweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: Gu danpeng
 * @data: 2018-9-18
 * @version：1.0
 */
@Controller
public class HomeController {
    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @RequestMapping("/home")
    public ModelAndView index() {
        ModelAndView m = new ModelAndView("home");
        m.addObject("message","Evan Gu");
        return m;
    }
}
