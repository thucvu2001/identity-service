package com.thucvu.identityservice.mapper;

import com.thucvu.identityservice.dto.request.UserCreationRequest;
import com.thucvu.identityservice.dto.request.UserUpdateRequest;
import com.thucvu.identityservice.dto.response.UserResponse;
import com.thucvu.identityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest userCreationRequest);
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);


//    @Mapping(source = "firstName", target = "firstName")
//    @Mapping(target = "firstName", ignore = true) // not map field firstName
    UserResponse toUserResponse(User user);
}
