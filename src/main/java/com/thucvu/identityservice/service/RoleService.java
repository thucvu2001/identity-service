package com.thucvu.identityservice.service;


import com.thucvu.identityservice.dto.request.RoleRequest;
import com.thucvu.identityservice.dto.response.RoleResponse;
import com.thucvu.identityservice.entity.Permission;
import com.thucvu.identityservice.entity.Role;
import com.thucvu.identityservice.mapper.RoleMapper;
import com.thucvu.identityservice.repository.PermissionRepository;
import com.thucvu.identityservice.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole (RoleRequest roleRequest) {
        Role role = roleMapper.toRole(roleRequest);
        List<Permission> permissionList = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissionList));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAllRole(){
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole (String roleName) {
        roleRepository.deleteById(roleName);
    }
}
