package com.evangu.raysweb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author: Gu danpeng
 * @data: 2018-9-18
 * @version：1.0
 */
@Configuration
@EnableOAuth2Client
public class OAuthConfiguration {

    @Value("${security.oauth2.tokenservice.checkTokenUrl}")
    String checkTokenUrl;
//
//    @Value("fooClientIdPassword")
//    String myClientId;
//
//    @Bean
//    @ConfigurationProperties("spring.oauth2.client")
//    public ClientCredentialsResourceDetails oauth2RemoteResource() {
//        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
//        details.setClientId(myClientId);
//        details.setClientSecret("123456");
//        return details;
//    }
//
////
////    @Bean
////    public OAuth2ClientContext oauth2ClientContext() {
////        return new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());
////    }
//
    @Bean
    @ConfigurationProperties("security.oauth2.client")
    @Primary
    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails(){
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
//        details.setClientId(myClientId);
//        details.setClientSecret("123456");
        return  details;
    }

    @Bean
    @Primary
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext context, OAuth2ProtectedResourceDetails details) {
        OAuth2RestTemplate template = new OAuth2RestTemplate(details, context);

        AuthorizationCodeAccessTokenProvider authCodeProvider = new AuthorizationCodeAccessTokenProvider();
        authCodeProvider.setStateMandatory(false);
        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(authCodeProvider));
        template.setAccessTokenProvider(provider);
        return template;
    }

    /**
     * 注册处理redirect uri的filter
     * @return
     */
    @Bean
    public OAuth2ClientAuthenticationProcessingFilter oauth2ClientAuthenticationProcessingFilter(
            OAuth2RestTemplate oAuth2RestTemplate,
            RemoteTokenServices tokenServices) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/account/connect/callback");
        filter.setRestTemplate(oAuth2RestTemplate);
        filter.setTokenServices(tokenServices);

        //设置回调成功的页面
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler() {
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                this.setDefaultTargetUrl("/home");
                System.out.println("OAuth2Client Authentication CallBack");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        });
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(){
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                System.out.println("OAuth2Client Authentication CallBack Failed");
                super.onAuthenticationFailure(request, response, exception);
            }
        });
        System.out.println("Filter OAuth2Client Created");
        return filter;
    }

    /**
     * 注册check token服务
     * @param details
     * @return
     */
    @Bean
    public RemoteTokenServices tokenService(OAuth2ProtectedResourceDetails details) {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(checkTokenUrl);
        tokenService.setClientId(details.getClientId());
        tokenService.setClientSecret(details.getClientSecret());
        return tokenService;
    }
}