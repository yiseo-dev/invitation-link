package com.invitation.service.user;

import com.invitation.entity.UserInfo;
import com.invitation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public UserInfo createUserInfo(UserInfo userInfo) {
        userRepository.saveUserInfo(userInfo);
        return userInfo;
    }

    public UserInfo findUserInfoById(Long userId) {
        return userRepository.findByUserId(userId);
    }

//    public void updateUserStatus(UserInfo userInfo) {
//        userRepository.update(userInfo);
//    }
}
