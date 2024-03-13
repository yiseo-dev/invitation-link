package com.zero.user;

import com.zero.entity.UserInfo;
import com.zero.repository.UserRepository;
import com.zero.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Test
    public void createUserInfo(){
        // 객체 설정
        UserInfo userInfo = new UserInfo(3L,"Sam","qweasd@gmail.com","01012312342","N");
        // 테스트 실행
        userService.createUserInfo(userInfo);
    }

    @Test
    public void findUserById(){
        UserInfo userInfo = new UserInfo(3L,"Sam","qweasd@gmail.com","01012312342","N");
        when(userRepository.findByUserId(3L)).thenReturn(userInfo);
        // 테스트 실행
        userInfo = userService.findUserInfoById(3L);
        // 검증
        assertThat(userInfo).isNotNull();
        // 검증
        verify(userRepository).findByUserId(3L);
    }
}
