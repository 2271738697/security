package com.example.security;

import com.example.security.Mapper.MenuMapper;
import com.example.security.entity.Role;
import com.example.security.entity.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.awt.*;
import java.util.Collection;
import java.util.List;

/**
 * @author zhoudb
 * @date 2019/12/23 16:59
 */
@Component
public class CustomFileterInvocationSecurityMetadaSourece implements FilterInvocationSecurityMetadataSource {
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    MenuMapper menuMapper;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<RoleMenu> allMenus = menuMapper.getAllMenus();
        for (RoleMenu menu : allMenus) {
            if (antPathMatcher.match(menu.getPattern(), requestUrl)) { //对权限进行装配，拥有权限则通过
                List<Role> roles = menu.getRoles();
                String[] roleArr = new String [roles.size()];//创一个容器装权限
                for (int i = 0; i < roleArr.length; i++) {
                    roleArr[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(roleArr);
            }

        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
