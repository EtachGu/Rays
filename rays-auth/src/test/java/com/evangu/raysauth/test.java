package com.evangu.raysauth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: Gu danpeng
 * @data: 2018-9-13
 * @version：1.0
 */
public class test{
    public static void main(String args[]){
        String pass = "123456";
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String hashPass = encode.encode(pass);
        System.out.println(hashPass);
    }
}
