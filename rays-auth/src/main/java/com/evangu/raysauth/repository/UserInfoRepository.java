package com.evangu.raysauth.repository;

import com.evangu.raysauth.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author: Gu danpeng
 * @data: 2018-9-26
 * @version：1.0
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);
}
