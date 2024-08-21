package com.thucvu.identityservice.service;

import com.thucvu.identityservice.dto.request.UserCreationRequest;
import com.thucvu.identityservice.dto.request.UserUpdateRequest;
import com.thucvu.identityservice.dto.response.UserResponse;
import com.thucvu.identityservice.entity.User;
import com.thucvu.identityservice.enums.Role;
import com.thucvu.identityservice.exception.AppException;
import com.thucvu.identityservice.exception.ErrorCode;
import com.thucvu.identityservice.mapper.UserMapper;
import com.thucvu.identityservice.repository.RoleRepository;
import com.thucvu.identityservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var listRole = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(listRole));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('APPROVE_POST')")
    public List<UserResponse> getUsers() {
        log.info("in method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    //    @PostAuthorize("hasRole('ADMIN')")
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
        log.info("in method get User by Id");
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }
}
