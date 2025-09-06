package com.gosaint.web.permissionManagement;

import com.github.pagehelper.PageInfo;
import com.gosaint.model.permissionManagement.Users;
import com.gosaint.service.permissionManagement.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户相关的API接口")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Operation(summary = "创建用户", description = "添加新用户到系统")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> createUser(@RequestBody Users user) {
        int result = usersService.createUser(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "根据ID获取用户", description = "通过用户ID查询用户详情")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.username")
    public ResponseEntity<Users> getUserById(@Parameter(description = "用户ID") @PathVariable String userId) {
        Users user = usersService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "获取所有用户", description = "查询系统中的所有用户列表")
    @GetMapping
   // @PreAuthorize("hasRole('ADMIN')") //TODO 权限控制
    public ResponseEntity<PageInfo<Users>> getAllUsers(int pageNum,int pageSize) {
        log.debug("获取所有用户start------");
        PageInfo<Users> page = usersService.getAllUsersByPage(pageNum, pageSize);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "更新用户", description = "修改用户信息")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or #user.username == authentication.principal.username")
    public ResponseEntity<Integer> updateUser(@RequestBody Users user) {
        log.debug("PUT /api/users called by principal");
        int result = usersService.updateUser(user);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除用户", description = "根据用户ID删除用户")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> deleteUser(@Parameter(description = "用户ID") @PathVariable String userId) {
        int result = usersService.deleteUser(userId);
        return ResponseEntity.ok(result);
    }
}