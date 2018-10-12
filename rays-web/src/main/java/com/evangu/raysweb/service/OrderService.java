package com.evangu.raysweb.service;

import com.evangu.raysweb.service.impl.OrderServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Gu danpeng
 * @data: 2018-9-18
 * @version：1.0
 */
@FeignClient(value = "rays-resource",fallback = OrderServiceImpl.class)
public interface OrderService {
    @RequestMapping(method = RequestMethod.GET, value = "/api/order/{id}", consumes = "application/json")
    public String getOrder(@PathVariable("id") String id, @RequestParam("access_token") String token);
}
