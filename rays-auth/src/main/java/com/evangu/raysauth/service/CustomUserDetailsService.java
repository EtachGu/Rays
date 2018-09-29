package com.evangu.raysauth.service;

import com.evangu.raysauth.entity.UserInfo;
import com.evangu.raysauth.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Gu danpeng
 * @data: 2018-9-26
 * @version：1.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user =
                repository.findByUsername(username);

        return user.orElseThrow(() ->
                new UsernameNotFoundException("user does not exists"));
    }
}
