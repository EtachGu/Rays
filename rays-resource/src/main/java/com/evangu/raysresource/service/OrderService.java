package com.evangu.raysresource.service;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author: Gu danpeng
 * @data: 2018-10-4
 * @version：1.0
 */
public interface OrderService {

    @PreAuthorize("hasPermission(#id,'String', read) or hasPermission(#id,'String', admin)")
    public String getOrder(String id);


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, admin)")
    public List<String> getAllOrder();

}
