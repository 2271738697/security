package com.example.security.Service;

import com.example.security.Mapper.UserMapper;
import com.example.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author zhoudb
 * @date 2019/12/23 16:59
 */

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

//    loadUserByUsername用户登录会自动调用
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("账户不存在！");
        }
        user.setRoles(userMapper.getUserRolesByUid(user.getId()));

       return user;
    }
}
