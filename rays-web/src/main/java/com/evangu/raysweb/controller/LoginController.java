package com.evangu.raysweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: Gu danpeng
 * @data: 2018-9-18
 * @version：1.0
 */
@Controller
public class LoginController {

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("login");
    }
}
