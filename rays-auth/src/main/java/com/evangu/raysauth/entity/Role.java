package com.evangu.raysauth.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author: Gu danpeng
 * @data: 2018-10-15
 * @version：1.0
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserInfo> users;

    @ManyToMany
    @JoinTable(
            name = "roles_permissioninfos",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "permissioninfo_id", referencedColumnName = "id"))
    private Collection<PermissionInfo> privileges;
}
