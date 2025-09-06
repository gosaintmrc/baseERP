package com.gosaint.web.permissionManagement;

import com.gosaint.model.permissionManagement.Permissions;
import com.gosaint.service.permissionManagement.PermissionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@Tag(name = "权限管理", description = "权限相关的API接口")
public class PermissionsController {
    @Autowired
    private PermissionsService permissionsService;

    @Operation(summary = "创建权限", description = "添加新权限到系统")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> createPermission(@RequestBody Permissions permission) {
        int result = permissionsService.createPermission(permission);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "根据ID获取权限", description = "通过权限ID查询权限详情")
    @GetMapping("/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Permissions> getPermissionById(@Parameter(description = "权限ID") @PathVariable String permissionId) {
        Permissions permission = permissionsService.getPermissionById(permissionId);
        return ResponseEntity.ok(permission);
    }

    @Operation(summary = "获取所有权限", description = "查询系统中的所有权限列表")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Permissions>> getAllPermissions() {
        List<Permissions> permissions = permissionsService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @Operation(summary = "更新权限", description = "修改权限信息")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> updatePermission(@RequestBody Permissions permission) {
        int result = permissionsService.updatePermission(permission);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除权限", description = "根据权限ID删除权限")
    @DeleteMapping("/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> deletePermission(@Parameter(description = "权限ID") @PathVariable String permissionId) {
        int result = permissionsService.deletePermission(permissionId);
        return ResponseEntity.ok(result);
    }
}