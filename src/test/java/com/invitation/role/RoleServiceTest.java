package com.invitation.role;

import com.invitation.entity.RoleInfo;
import com.invitation.repository.RoleRepository;
import com.invitation.service.role.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;
    @Test
    public void findRoleByUserId(){
        RoleInfo expectedRoleInfo = new RoleInfo(1L, "AD", 1L);
        when(roleRepository.findRoleById(1L)).thenReturn(expectedRoleInfo);
        // 테스트 실행
        RoleInfo roleInfo = roleService.findRoleByUserId(1L);
        // 검증
        assertThat(roleInfo).isNotNull();
        // 검증
        verify(roleRepository).findRoleById(1L);

    }

    @Test
    public void saveRole() {
        // 객체 설정
        RoleInfo roleInfo = new RoleInfo(2L,"ME",2L);
        // 테스트 실행
        roleService.saveRole(roleInfo);
        // 검증
        verify(roleRepository).save(roleInfo);
    }
}
