package com.example.security.entity;

import lombok.Data;

import java.util.List;

/**
 * @author zhoudb
 * @date 2019/12/23 16:59
 */
@Data
public class RoleMenu {
    private int id;
    private String pattern;
    private List<Role> roles;



}
