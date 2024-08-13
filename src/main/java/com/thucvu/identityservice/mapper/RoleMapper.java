package com.thucvu.identityservice.mapper;


import com.thucvu.identityservice.dto.request.RoleRequest;
import com.thucvu.identityservice.dto.response.RoleResponse;
import com.thucvu.identityservice.entity.Role;
import com.thucvu.identityservice.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
