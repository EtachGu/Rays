package com.evangu.raysweb.service.impl;

import com.evangu.raysweb.service.OrderService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: Gu danpeng
 * @data: 2018-10-11
 * @version：1.0
 */
@Component
public class OrderServiceImpl implements OrderService{
    @Override
    public String getOrder(String id, String token){
        return "Order can not get";
    }
}
