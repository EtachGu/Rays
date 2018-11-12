package com.evangu.raysauth.service;

import com.evangu.raysauth.jedis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author: Gu danpeng
 * @data: 2018-11-12
 * @version：1.0
 */
public class RaysTokenEnhancer implements TokenEnhancer {

    @Autowired
    RedisService redisService;

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    private JsonParser objectMapper = JsonParserFactory.create();

    private String verifierKey = "test";//(new RandomValueStringGenerator()).generate();
    private Signer signer;
    private String signingKey;
    private SignatureVerifier verifier;

    public RaysTokenEnhancer(){
        this.signer = new MacSigner(this.verifierKey);
        this.signingKey = this.verifierKey;
    }

    public void setSigningKey(String key) {
        Assert.hasText(key);
        key = key.trim();
        this.signingKey = key;
        if (this.isPublic(key)) {
            this.signer = new RsaSigner(key);
//            logger.info("Configured with RSA signing key");
        } else {
            this.verifierKey = key;
            this.signer = new MacSigner(key);
        }

    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//        final Map<String, Object> additionalInfo = new HashMap<>();
//        additionalInfo.put("organization", authentication.getName() + randomAlphabetic(4));
//        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        String jwt = this.encode(accessToken,authentication);
        this.redisService.set(accessToken.getValue(),jwt);
        System.out.println("===RaysTokenEnhancer===");
        System.out.println("===RaysTokenEnhancer Jwt=== \n" + jwt);
        return accessToken;
    }

    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = this.objectMapper.formatMap(this.tokenConverter.convertAccessToken(accessToken, authentication));
        } catch (Exception var5) {
            throw new IllegalStateException("Cannot convert access token to JSON", var5);
        }

        String token = JwtHelper.encode(content, this.signer).getEncoded();
        return token;
    }

    protected Map<String, Object> decode(String token) {
        try {
            Jwt jwt = JwtHelper.decodeAndVerify(token, this.verifier);
            String claimsStr = jwt.getClaims();
            Map<String, Object> claims = this.objectMapper.parseMap(claimsStr);
            if (claims.containsKey("exp") && claims.get("exp") instanceof Integer) {
                Integer intValue = (Integer)claims.get("exp");
                claims.put("exp", new Long((long)intValue));
            }

//            this.getJwtClaimsSetVerifier().verify(claims);
            return claims;
        } catch (Exception var6) {
            throw new InvalidTokenException("Cannot convert access token to JSON", var6);
        }
    }

    private boolean isPublic(String key) {
        return key.startsWith("-----BEGIN");
    }

    public boolean isPublic() {
        return this.signer instanceof RsaSigner;
    }
}
