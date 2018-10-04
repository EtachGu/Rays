package com.evangu.raysresource.service.impl;

import com.evangu.raysresource.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author: Gu danpeng
 * @data: 2018-10-4
 * @version：1.0
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Override
    public String getOrder(String id){
        return "order is " + id;
    }
}
