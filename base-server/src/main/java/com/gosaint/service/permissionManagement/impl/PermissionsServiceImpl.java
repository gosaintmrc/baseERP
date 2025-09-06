package com.gosaint.service.permissionManagement.impl;

import com.gosaint.dao.permissionManagement.PermissionsDao;
import com.gosaint.model.permissionManagement.Permissions;
import com.gosaint.service.permissionManagement.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionsServiceImpl implements PermissionsService {
    @Autowired
    private PermissionsDao permissionsDao;

    @Override
    public int createPermission(Permissions permission) {
        return permissionsDao.insert(permission);
    }

    @Override
    public Permissions getPermissionById(String permissionId) {
        return permissionsDao.selectById(permissionId);
    }

    @Override
    public Permissions getPermissionByName(String permissionName) {
        return permissionsDao.selectByName(permissionName);
    }

    @Override
    public List<Permissions> getAllPermissions() {
        return permissionsDao.selectAll();
    }

    @Override
    public int updatePermission(Permissions permission) {
        return permissionsDao.update(permission);
    }

    @Override
    public int deletePermission(String permissionId) {
        return permissionsDao.delete(permissionId);
    }
}