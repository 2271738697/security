package com.example.security.Mapper;

import com.example.security.entity.Role;
import com.example.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
//    @Select("select * from user where  username = #{username}")
    User loadUserByUsername(String username);
//    @Select("select * from role r,user_role ur where r.id = ur.rid and ur.uid = #{id}")
    List<Role> getUserRolesByUid(Integer id);


}
