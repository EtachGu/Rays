package com.evangu.raysauth.service;

import com.evangu.raysauth.entity.Permission;
import com.evangu.raysauth.entity.Role;
import com.evangu.raysauth.entity.UserInfo;
import com.evangu.raysauth.repository.RoleRepository;
import com.evangu.raysauth.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author: Gu danpeng
 * @data: 2018-9-26
 * @version：1.0
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserInfoRepository userInfoRepository;

    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user =
                userInfoRepository.findByUsername(username);

        user.get().setPermissions(this.getPermissions(user.get().getRoles()));

//        UserDetails userDetails = new User(user.get().getUsername(),
//                user.get().getPassword(),
//                this.getAuthorities(user.get()));

        return user.orElseThrow(()->
                new UsernameNotFoundException("User is not existed")
        );
    }

    public Collection<? extends GrantedAuthority> getAuthorities(UserInfo userInfo) {
        return this.getGrantedAuthorities(getPermissions(userInfo.getRoles()));
    }

    private List<String> getPermissions(Collection<Role> roles) {
        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            permissions.add(item.getName());
        }
        return permissions;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    public UserDetails registerNewUser(String username, String password) {

        UserInfo user = new UserInfo();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER").get()));
        return userInfoRepository.save(user);
    }
}
