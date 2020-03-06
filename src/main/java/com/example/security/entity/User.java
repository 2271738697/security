package com.example.security.entity;

import com.example.security.entity.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zhoudb
 * @date 2019/12/23 16:59
 */
@Data
public class User implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean locked;
    private List<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    /**
     * 用户账户是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * 用户是否锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }
    /**
     * 用户密码是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * 用户是否可用
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
