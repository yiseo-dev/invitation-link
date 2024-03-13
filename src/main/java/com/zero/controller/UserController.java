package com.zero.controller;

import com.zero.config.Response;
import com.zero.entity.UserInfo;
import com.zero.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/generate")
    public ResponseEntity<Response> generateInvitation(@RequestBody UserInfo request) {
        UserInfo userInfo = userService.createUserInfo(request);
        return ResponseEntity.ok(Response.builder().data(userInfo).build());
    }

    @GetMapping("/{userId}/info")
    public ResponseEntity<Response> findUserInfoById(@PathVariable(value = "userId") Integer userId) {
        UserInfo userInfo = userService.findUserInfoById(userId);
        return ResponseEntity.ok(Response.builder().data(userInfo).build());
    }

//    @PatchMapping("/{userId}/status")
//    public ResponseEntity<HttpStatus> updateUserStatus(@PathVariable(value = "userId") Integer userId, UserInfo userInfo) {
//        userInfo.setUserId(userId);
//        userService.updateUserStatus(userInfo);
//        return ResponseEntity.ok(HttpStatus.ACCEPTED);
//    }
}
