package com.evangu.raysauth.repository;

import com.evangu.raysauth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author: Gu danpeng
 * @data: 2018-10-18
 * @version：1.0
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
