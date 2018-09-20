package com.evangu.raysweb.controller;

import com.fasterxml.jackson.core.JsonParser;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.MessageFormat;

/**
 * @author: Gu danpeng
 * @data: 2018-9-18
 * @version：1.0
 */
@Controller
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("fooClientIdPassword")
    private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    @RequestMapping("/login")
    public ModelAndView index() {
        return new ModelAndView("login");
    }

//    @RequestMapping("/login")
//    public void login(@RequestHeader Object  requestHeader) {
////        restTemplate.getAccessToken();
//    }

    @RequestMapping("/account/connect")
    public String reConnect(RedirectAttributes redirectAttributes){
        String url = "http://localhost:8081/oauth/authorize";
        redirectAttributes.addAttribute("client_id", "fooClientIdPassword");
        redirectAttributes.addAttribute("response_type", "code");
        redirectAttributes.addAttribute("scope", "read");
        redirectAttributes.addAttribute("redirect_uri", "http://localhost:8083/account/connect/callback");
        return "redirect:" + url;
    }

    @RequestMapping("/account/connect/callback")
    public String getAccessKeyAndGoHome(@RequestParam("code") String authenticationCode,RedirectAttributes redirectAttributes){
        String urlTemplate = "http://localhost:8081/oauth/token?grant_type=authorization_code&scope=read&code={0}&client_id={1}&client_secret={2}";

        String url = MessageFormat.format(urlTemplate,authenticationCode,clientId,clientSecret);
        System.out.println(url);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,String.class);

        String responseBody = responseEntity.getBody();
        System.out.println(responseBody);
        return responseBody;
    }
}