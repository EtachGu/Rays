package com.evangu.raysweb.service.hystrix;

import com.evangu.raysweb.service.OrderService;
import org.springframework.stereotype.Component;

/**
 * @author: Gu danpeng
 * @data: 2018-10-11
 * @version：1.0
 */
@Component
public class OrderServiceHystrix implements OrderService{
    @Override
    public String getOrder(String id, String token){
        return "Order can not get";
    }
}
