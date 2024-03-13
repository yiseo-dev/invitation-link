package com.zero.role;

import com.zero.entity.RoleInfo;
import com.zero.repository.RoleRepository;
import com.zero.service.role.RoleService;
import org.junit.jupiter.api.BeforeEach;
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
    @BeforeEach
    public void setUp() {
        RoleInfo expectedRoleInfo = new RoleInfo(1, "AD", 1);
        when(roleRepository.findRoleById(1)).thenReturn(expectedRoleInfo);
    }
    @Test
    public void findRoleByUserId(){
        // 테스트 실행
        RoleInfo roleInfo = roleService.findRoleByUserId(1);
        // 검증
        assertThat(roleInfo).isNotNull();
        // 검증
        verify(roleRepository).findRoleById(1);

    }

    @Test
    public void saveRole() {
        // 객체 설정
        RoleInfo roleInfo = new RoleInfo(2,"ME",2);
        // 테스트 실행
        roleService.saveRole(roleInfo);
        // 검증
        verify(roleRepository).save(roleInfo);
    }
}
