package com.zero.invitation;

import com.zero.entity.InvitationInfo;
import com.zero.entity.RoleInfo;
import com.zero.entity.UserInfo;
import com.zero.exception.CustomException;
import com.zero.model.invitation.request.InvitationRequest;
import com.zero.model.invitation.request.JoinRequest;
import com.zero.model.invitation.response.InvitationResponse;
import com.zero.repository.InvitationRepository;
import com.zero.service.invitation.InvitationService;
import com.zero.service.role.RoleService;
import com.zero.service.user.UserService;
import com.zero.utils.RedisUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.zero.exception.ErrorEnum.NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvitationServiceTest {

    @Mock
    private RedisUtils redisUtils;
    @Mock
    private InvitationRepository invitationRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private UserService userService;
    @InjectMocks
    private InvitationService invitationService;

    @BeforeEach
    void setUp(){
    }

    @Test
    void createInvitationLink_Success(){
        // 객체 설정
        InvitationRequest request = new InvitationRequest(1,2,"01099999999","Choi","aaaa@naver.com");
        UserInfo userInfo = new UserInfo(2,"Choi","aaaa@naver.com","01099999999","N");
        RoleInfo roleInfo = new RoleInfo(1,"AD",1);
        InvitationInfo savedInvitationInfo = new InvitationInfo(1,1,2,"S","20240312000000");

        when(roleService.findRoleByUserId(anyInt())).thenReturn(roleInfo);
        when(userService.createUserInfo(any(UserInfo.class))).thenReturn(userInfo);
        when(invitationRepository.save(any(InvitationInfo.class))).thenReturn(savedInvitationInfo);
        doNothing().when(redisUtils).setData(anyString(), anyString(), anyLong());

        // 테스트 실행
        InvitationResponse response = invitationService.createInvitationLink(request);

        // 검증
        assertThat(response).isNotNull();
        assertThat(response.getLink()).isNotEmpty();

        // 모의 객체의 상호작용 검증
        verify(roleService).findRoleByUserId(anyInt());
        verify(userService).createUserInfo(any(UserInfo.class));
        verify(invitationRepository).save(any(InvitationInfo.class));
        verify(redisUtils).setData(anyString(), anyString(), anyLong());
    }

    @Test
    void createInvitationLink_Failure_WhenRoleNotFound() {
        // 객체 설정
        InvitationRequest request = new InvitationRequest(1,2,"01099999999","Choi","aaaa@naver.com");

        when(roleService.findRoleByUserId(anyInt())).thenThrow(new CustomException(NOT_FOUND));

        // 예외 검증
        assertThrows(CustomException.class, () -> invitationService.createInvitationLink(request));

        // 검증
        verify(roleService).findRoleByUserId(anyInt());
        verifyNoInteractions(userService);
        verifyNoInteractions(invitationRepository);
        verifyNoInteractions(redisUtils);
    }

    @Test
    void joinInvitation_Success(){
        // 객체 설정
        JoinRequest joinRequest = new JoinRequest(2,1,"userId=2&invitationId=1&043618d2-dbb3-4712-85e9-00e950f63685",true);
        InvitationInfo existingInvitationInfo = new InvitationInfo(1,1,2,"S","20240312000000");
        UserInfo userInfo = new UserInfo(2,"Choi","aaaa@naver.com","01099999999","N");
        String savedLink = "userId=2&invitationId=1&043618d2-dbb3-4712-85e9-00e950f63685"; // 예상되는 링크 값

        // 모의 객체의 행동 설정
        when(redisUtils.getData("userId=%d&invitationId=%d&".formatted(joinRequest.getUserId(), joinRequest.getInvitationId())))
                .thenReturn(savedLink);
        when(invitationRepository.findByUserId(anyInt())).thenReturn(existingInvitationInfo);
        when(userService.findUserInfoById(anyInt())).thenReturn(userInfo);
        doNothing().when(redisUtils).deleteData(anyString());

        // 테스트 실행
        invitationService.joinInvitation(joinRequest);

        // 검증
        verify(invitationRepository).findByUserId(anyInt());
        verify(userService).findUserInfoById(anyInt());
        verify(redisUtils).deleteData(anyString());
    }

    @Test
    void joinInvitation_Failure_WhenLinkInvalid() {
        // 객체 설정
        JoinRequest joinRequest = new JoinRequest(18,1,"userId=18&invitationId=23&811fdcee-b65a-46a1-af6c-398d78a14ddc",true);

        // 테스트 실행 및 예외 검증
        assertThrows(CustomException.class, () -> invitationService.joinInvitation(joinRequest));

        // 검증
        verifyNoInteractions(invitationRepository);
        verifyNoInteractions(userService);
        verify(redisUtils).getData(anyString());
    }


}
