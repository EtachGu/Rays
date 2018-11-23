package com.evangu.raysgate.filter;

import com.evangu.raysgate.jedis.RedisService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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
        final String jwt = redisService.get(request.getParameter("access_token"));

        System.out.println("\t JWT:" + jwt);
        ctx.addZuulRequestHeader("token",jwt);
        return null;
    }

}
