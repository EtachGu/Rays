package com.evangu.raysauth.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author: Gu danpeng
 * @data: 2018-10-15
 * @version：1.0
 */
@Entity
@Table(name = "permissioninfo")
public class PermissionInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "permissioninfos")
    private Collection<Role> roles;
}
