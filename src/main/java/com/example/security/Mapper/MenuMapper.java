package com.example.security.Mapper;

import com.example.security.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.awt.*;
import java.util.List;

/**
 * @author zhoudb
 * @date 2019/12/23 16:59
 */
@Mapper
public interface MenuMapper {
    List<RoleMenu> getAllMenus();

}

