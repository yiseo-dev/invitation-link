package com.invitation.service.invitation;

import com.invitation.entity.InvitationInfo;
import com.invitation.entity.RoleInfo;
import com.invitation.entity.UserInfo;
import com.invitation.exception.CustomException;
import com.invitation.model.invitation.request.LinkInfoRequest;
import com.invitation.model.invitation.request.InvitationRequest;
import com.invitation.model.invitation.request.JoinRequest;
import com.invitation.model.invitation.response.InvitationResponse;
import com.invitation.model.invitation.response.LinkInfoResponse;
import com.invitation.repository.InvitationRepository;
import com.invitation.service.role.RoleService;
import com.invitation.service.user.UserService;
import com.invitation.utils.RedisUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.invitation.exception.ErrorEnum.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvitationService {
    private final RedisUtils redisUtils;
    private final InvitationRepository invitationRepository;
    private final UserService userService;
    private final RoleService roleService;

    private final  String INVITATION_LINK_PREFIX = "userId=%d&invitationId=%d&";

    @Transactional
    public InvitationResponse createInvitationLink(InvitationRequest request) {
        UserInfo userInfo = new UserInfo();

        // 역할 정보 조회
        RoleInfo role = roleService.findRoleByUserId(request.getInviterId());
        if("AD".equals(role.getRoleName())) {
            userInfo = userService.createUserInfo(UserInfo.builder()
                    .useYn("N")
                    .userName(request.getInviteeName())
                    .userPhoneNumber(request.getInviteePhoneNumber())
                    .userEmail(request.getInviteeEmail())
                    .build());
        }else {
            throw new CustomException(NOT_FOUND);
        }
        // 초대 정보 저장
        InvitationInfo invitationInfo = invitationRepository.save(InvitationInfo.builder()
                .inviterId(request.getInviterId())
                .invitationState("S")
                .expireDtm(this.makeExpiredDtm())
                .inviteeId(userInfo.getUserId())
                .build());
        // Unique 초대 코드 생성
        String linkCode = UUID.randomUUID().toString();
        // Redis에 초대 코드 저장, 24시간 후 만료되도록 설정
        redisUtils.setData(INVITATION_LINK_PREFIX.formatted(invitationInfo.getInviteeId(),invitationInfo.getInvitationId())
                , INVITATION_LINK_PREFIX.formatted(invitationInfo.getInviteeId(),invitationInfo.getInvitationId()).concat(linkCode), 24L);

        return InvitationResponse.builder().link(INVITATION_LINK_PREFIX.formatted(invitationInfo.getInviteeId(),invitationInfo.getInvitationId()).concat(linkCode)).build();
    }

    @Transactional
    public void joinInvitation(JoinRequest request) {
        // 링크 유효성 체크
        if(this.validLink(request)){
            // 링크 만료
            redisUtils.deleteData(INVITATION_LINK_PREFIX.formatted(request.getUserId(),request.getInvitationId()));
            // 초대 정보 조회
            InvitationInfo invitationInfo = invitationRepository.findByUserId(request.getInvitationId());
            if(request.getIsJoin()) {
                // 회원 활성화
                UserInfo userInfo = userService.findUserInfoById(request.getUserId());
                if(!ObjectUtils.isEmpty(userInfo)) {
                    userInfo.setUseYn("Y");
//                    userService.updateUserStatus(userInfo);
                    roleService.saveRole(RoleInfo.builder().roleName("ME").userId(userInfo.getUserId()).build());
                }

                if(!ObjectUtils.isEmpty(invitationInfo)) {
                    invitationInfo.setInvitationState("J");
                }
            }else {
                invitationInfo.setInvitationState("R");
            }
        }else{
            throw new CustomException(NOT_MATCH_LINK);
        }

    }

    public LinkInfoResponse findLinkById(LinkInfoRequest request) {
        String link = redisUtils.getData(INVITATION_LINK_PREFIX.formatted(request.getUserId(),request.getInvitationId()));
        if(link != null){
            return LinkInfoResponse.builder().link(link).build();
        }else {
            throw new CustomException(EXPIRED_LINK);
        }
    }

    // 만료 기간
    private String makeExpiredDtm() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusHours(24);
        return tomorrow.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
    // 링크 유효성 체크
    private Boolean validLink(JoinRequest joinRequest) {
        String savedLink = redisUtils.getData(INVITATION_LINK_PREFIX.formatted(joinRequest.getUserId(),joinRequest.getInvitationId()));
        if(savedLink!=null) {
            return savedLink.equals(joinRequest.getLink());
        }else {
            throw new CustomException(EXPIRED_LINK);
        }
    }
}
