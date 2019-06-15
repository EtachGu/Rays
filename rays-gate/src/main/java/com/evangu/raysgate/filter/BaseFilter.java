package com.evangu.raysgate.filter;

import com.evangu.raysgate.jedis.RedisService;
import com.netflix.ribbon.proxy.annotation.Http;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: Gu danpeng
 * @data: 2018-11-20
 * @version：1.0
 */
@Component
public class BaseFilter extends ZuulFilter {

    @Autowired
    private RedisService redisService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI();
        final String method = request.getMethod();
        System.out.println("====test Zuul Filter" + requestUri);



        String access_token = parseToken(request.getHeader("Authorization"));
        if(access_token == null) {
            access_token = request.getParameter("access_token");
        }
        if(access_token == null) {
//            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            try {
                ctx.getResponse().sendError(HttpStatus.UNAUTHORIZED.value());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        final String jwt = redisService.get(access_token);
        if(jwt == null) {
            try {
                ctx.getResponse().sendError(HttpStatus.UNAUTHORIZED.value());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        System.out.println("\t JWT:" + jwt);
//        ctx.addZuulRequestHeader("token",jwt);
        ctx.addZuulRequestHeader("Authorization", "Bearer " + jwt);
        return null;
    }

    private static String parseToken(String authorization) {
        if(authorization == null || authorization.isEmpty()) {
            return null;
        }
        String[] strs = authorization.split(" ");
        if(strs.length == 2)  {
            if("bearer".equalsIgnoreCase(strs[0])){
                return strs[1];
            }
        }
        return null;
    }

}
