package com.evangu.raysresource.service.impl;

import com.evangu.raysresource.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    @Override
    public List<String> getAllOrder() {
        String[] result = {"Order is 1","Order is 2"};
        return Arrays.asList(result);
    }
}
