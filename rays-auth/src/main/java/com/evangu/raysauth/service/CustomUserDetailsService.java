package com.evangu.raysauth.service;

import com.evangu.raysauth.entity.UserInfo;
import com.evangu.raysauth.repository.RoleRepository;
import com.evangu.raysauth.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author: Gu danpeng
 * @data: 2018-9-26
 * @version：1.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserInfoRepository userInfoRepository;

    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user =
                userInfoRepository.findByUsername(username);

        return user.orElseThrow(() ->
                new UsernameNotFoundException("user does not exists"));
    }

    public UserDetails registerNewUser(String username, String password) {

        UserInfo user = new UserInfo();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER").get()));
        return userInfoRepository.save(user);
    }
}
