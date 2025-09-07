package com.gosaint.service.permissionManagement.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gosaint.dao.permissionManagement.UsersDao;
import com.gosaint.model.permissionManagement.Users;
import com.gosaint.model.vo.UsersVO;
import com.gosaint.service.permissionManagement.UserRoleService;
import com.gosaint.service.permissionManagement.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao usersDao;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public int createUser(Users user) {
        return usersDao.insert(user);
    }

    @Override
    @Transactional
    public int createUser(UsersVO user) {
        //对用户密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(user.getPasswordHash());
        user.setPasswordHash(encode);
        user.setCreatedAt(OffsetDateTime.now());
        int result = usersDao.insert(user);
        Users users = usersDao.selectByUsername(user.getUsername());
        userRoleService.addRoleToUser(users.getUserId(), user.getRoleId());
        return result;
    }

    @Override
    public Users getUserById(String userId) {
        return usersDao.selectById(userId);
    }

    @Override
    public Users getUserByUsername(String username) {
        return usersDao.selectByUsername(username);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersDao.selectAll();
    }

    @Override
    public PageInfo<UsersVO> getAllUsersAndRoles(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UsersVO> users = usersDao.selectAndRolesAll();
        return new PageInfo<>(users);
    }

    @Override
    public PageInfo<Users> getAllUsersByPage(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Users> users = usersDao.selectAll();
        return new PageInfo<>(users);
    }

    @Override
    public int updateUser(Users user) {
        return usersDao.update(user);
    }

    @Override
    public int deleteUser(String userId) {
        return usersDao.delete(userId);
    }
}