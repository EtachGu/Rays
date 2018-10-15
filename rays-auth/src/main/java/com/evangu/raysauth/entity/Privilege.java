package com.evangu.raysauth.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author: Gu danpeng
 * @data: 2018-10-15
 * @version：1.0
 */
@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
