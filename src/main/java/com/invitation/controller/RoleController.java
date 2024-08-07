package com.invitation.controller;

import com.invitation.config.Response;
import com.invitation.entity.RoleInfo;
import com.invitation.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{userId}")
    public ResponseEntity<Response> findRoleByUserId(@PathVariable(value = "userId") Long userId) {
        RoleInfo roleInfo = roleService.findRoleByUserId(userId);
        return ResponseEntity.ok(Response.builder().build());
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createRole(RoleInfo roleInfo) {
        roleService.saveRole(roleInfo);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
