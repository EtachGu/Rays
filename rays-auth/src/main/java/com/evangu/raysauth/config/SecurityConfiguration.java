package com.evangu.raysauth.config;

import com.evangu.raysauth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Gu danpeng
 * @data: 2018-9-13
 * @version：1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    CustomUserDetailsService userDetailsService;

//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user_1").password(new BCryptPasswordEncoder().encode("123456")).authorities("USER").build());
//        manager.createUser(User.withUsername("user_2").password(new BCryptPasswordEncoder().encode("123456")).authorities("USER").build());
//        return manager;
//    }





    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        AuthenticationEntryPoint aep = new AuthenticationEntryPoint() {
//
//            @Override
//            public void commence(HttpServletRequest request,
//                                 HttpServletResponse response,
//                                 AuthenticationException authException) throws IOException,
//                    ServletException {
//                response.sendRedirect("/login");
//            }
//        };
//        // @formatter:off
////            http.csrf().disable();
////            http
////                .requestMatchers().antMatchers("/", "/oauth/**","/login/**","/logout/**")
////                .and()
////                .authorizeRequests()
////                .antMatchers("/oauth/**").authenticated()
////                .and()
////                .exceptionHandling()
////                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"));
        http.authorizeRequests().antMatchers("/**").hasRole("USER").and().formLogin();
        // @formatter:on
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //inMemoryAuthentication 从内存中获取
////        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user_1").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
//        //注入userDetailsService的实现类
//        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}