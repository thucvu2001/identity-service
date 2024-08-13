package com.thucvu.identityservice.mapper;

import com.thucvu.identityservice.dto.request.PermissionRequest;
import com.thucvu.identityservice.dto.request.UserCreationRequest;
import com.thucvu.identityservice.dto.request.UserUpdateRequest;
import com.thucvu.identityservice.dto.response.PermissionResponse;
import com.thucvu.identityservice.dto.response.UserResponse;
import com.thucvu.identityservice.entity.Permission;
import com.thucvu.identityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
