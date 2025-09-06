package com.gosaint.service.permissionManagement;

import com.github.pagehelper.PageInfo;
import com.gosaint.model.permissionManagement.Users;
import java.util.List;

public interface UsersService {
    // 创建用户
    int createUser(Users user);

    // 根据ID查询用户
    Users getUserById(String userId);

    // 根据用户名查询用户
    Users getUserByUsername(String username);

    // 查询所有用户
    List<Users> getAllUsers();

    //分页
    PageInfo<Users> getAllUsersByPage(int pageNum,int pageSize);

    // 更新用户
    int updateUser(Users user);

    // 删除用户
    int deleteUser(String userId);
}