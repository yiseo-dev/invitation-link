package com.invitation.service.role;

import com.invitation.entity.RoleInfo;
import com.invitation.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleInfo findRoleByUserId(Long userId) {
        return roleRepository.findRoleById(userId);
    }

    public void saveRole(RoleInfo roleInfo) {
        roleRepository.save(roleInfo);
    }
}
