package com.evangu.raysresource.service;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author: Gu danpeng
 * @data: 2018-10-4
 * @version：1.0
 */
public interface OrderService {

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, admin)")
    public String getOrder(String id);
}
