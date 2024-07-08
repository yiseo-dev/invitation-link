package com.invitation.invitation;

import com.invitation.entity.InvitationInfo;
import com.invitation.entity.RoleInfo;
import com.invitation.entity.UserInfo;
import com.invitation.exception.CustomException;
import com.invitation.model.invitation.request.InvitationRequest;
import com.invitation.model.invitation.request.JoinRequest;
import com.invitation.model.invitation.response.InvitationResponse;
import com.invitation.repository.InvitationRepository;
import com.invitation.service.invitation.InvitationService;
import com.invitation.service.role.RoleService;
import com.invitation.service.user.UserService;
import com.invitation.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.invitation.exception.ErrorEnum.NOT_FOUND;
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

    @Test
    void createInvitationLink_success(){
        // 객체 설정
        InvitationRequest request = new InvitationRequest(1L,2L,"01099999999","Choi","aaaa@naver.com");
        UserInfo userInfo = new UserInfo(2L,"Choi","aaaa@naver.com","01099999999","N");
        RoleInfo roleInfo = new RoleInfo(1L,"AD",1L);
        InvitationInfo savedInvitationInfo = new InvitationInfo(1L,1L,2L,"S","20240312000000");

        when(roleService.findRoleByUserId(anyLong())).thenReturn(roleInfo);
        when(userService.createUserInfo(any(UserInfo.class))).thenReturn(userInfo);
        when(invitationRepository.save(any(InvitationInfo.class))).thenReturn(savedInvitationInfo);
        doNothing().when(redisUtils).setData(anyString(), anyString(), anyLong());

        // 테스트 실행
        InvitationResponse response = invitationService.createInvitationLink(request);

        // 검증
        assertThat(response).isNotNull();
        assertThat(response.getLink()).isNotEmpty();

        // 검증
        verify(roleService).findRoleByUserId(anyLong());
        verify(userService).createUserInfo(any(UserInfo.class));
        verify(invitationRepository).save(any(InvitationInfo.class));
        verify(redisUtils).setData(anyString(), anyString(), anyLong());
    }

    @Test
    void createInvitationLink_failure_whenRoleNotFound() {
        // 객체 설정
        InvitationRequest request = new InvitationRequest(1L,2L,"01099999999","Choi","aaaa@naver.com");

        when(roleService.findRoleByUserId(anyLong())).thenThrow(new CustomException(NOT_FOUND));

        // 예외 검증
        assertThrows(CustomException.class, () -> invitationService.createInvitationLink(request));

        // 검증
        verify(roleService).findRoleByUserId(anyLong());
        verifyNoInteractions(userService);
        verifyNoInteractions(invitationRepository);
        verifyNoInteractions(redisUtils);
    }

    @Test
    void joinInvitation_success(){
        // 객체 설정
        JoinRequest joinRequest = new JoinRequest(2L,1L,"userId=2&invitationId=1&043618d2-dbb3-4712-85e9-00e950f63685",true);
        InvitationInfo existingInvitationInfo = new InvitationInfo(1L,1L,2L,"S","20240312000000");
        UserInfo userInfo = new UserInfo(2L,"Choi","aaaa@naver.com","01099999999","N");
        String savedLink = "userId=2&invitationId=1&043618d2-dbb3-4712-85e9-00e950f63685"; // 예상되는 링크 값

        // 모의 객체의 행동 설정
        when(redisUtils.getData("userId=%d&invitationId=%d&".formatted(joinRequest.getUserId(), joinRequest.getInvitationId())))
                .thenReturn(savedLink);
        when(invitationRepository.findByUserId(anyLong())).thenReturn(existingInvitationInfo);
        when(userService.findUserInfoById(anyLong())).thenReturn(userInfo);
        doNothing().when(redisUtils).deleteData(anyString());

        // 테스트 실행
        invitationService.joinInvitation(joinRequest);

        // 검증
        verify(invitationRepository).findByUserId(anyLong());
        verify(userService).findUserInfoById(anyLong());
        verify(redisUtils).deleteData(anyString());
    }

    @Test
    void joinInvitation_failure_whenLinkInvalid() {
        // 객체 설정
        JoinRequest joinRequest = new JoinRequest(18L,1L,"userId=18&invitationId=23&811fdcee-b65a-46a1-af6c-398d78a14ddc",true);

        // 테스트 실행 및 예외 검증
        assertThrows(CustomException.class, () -> invitationService.joinInvitation(joinRequest));

        // 검증
        verifyNoInteractions(invitationRepository);
        verifyNoInteractions(userService);
        verify(redisUtils).getData(anyString());
    }


}
