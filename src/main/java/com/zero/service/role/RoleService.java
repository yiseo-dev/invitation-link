package com.zero.service.role;

import com.zero.entity.RoleInfo;
import com.zero.repository.RoleRepository;
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
