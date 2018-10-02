package com.evangu.raysweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
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
 * @data: 2018-10-2
 * @version：1.0
 */
@Configuration
@EnableOAuth2Client
public class RaysUsernamePasswordConfig {

    private OAuth2RestTemplate template;
    public OAuth2ClientAuthenticationProcessingFilter filter;

    @Value("${security.oauth2.client.clientId}")
    private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;

    @Value("${security.oauth2.client.userAuthorizationUri}")
    private String userAuthorizationUri;

    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails(){
        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
        details.setClientId(this.clientId);
        details.setClientSecret(this.clientSecret);
        details.setAccessTokenUri(this.accessTokenUri);
        return  details;
    }

    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext context) {
        OAuth2ProtectedResourceDetails details = this.oAuth2ProtectedResourceDetails();
        OAuth2RestTemplate template = new OAuth2RestTemplate(details,context);
        ResourceOwnerPasswordAccessTokenProvider authCodeProvider = new ResourceOwnerPasswordAccessTokenProvider();
        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(authCodeProvider));
        template.setAccessTokenProvider(provider);
        this.template = template;
        return template;
    }

    /**
     * 注册处理ResourceUsernamepassword的filter
     * @return
     */
    @Autowired
    public OAuth2ClientAuthenticationProcessingFilter oauth2ClientAuthenticationProcessingFilter(OAuth2ClientContext context,RemoteTokenServices tokenServices) {
        this.template = this.oauth2RestTemplate(context);
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/account/login");
        filter.setRestTemplate(template);
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
        this.filter = filter;
        return filter;
    }
}
