package com.dev.demo.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dev.demo.auth.response.UserInfoResponse;
import com.dev.demo.auth.security.services.UserDetailsImpl;
import com.dev.demo.user.model.User;
import com.dev.demo.user.validation.request.CreateUpdateRequest;
import com.dev.demo.user.validation.request.SignupRequest;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toRegister(SignupRequest signUpRequest);

    User toUser(CreateUpdateRequest createUpdateRequest);

    UserInfoResponse toUserInfoResponse(UserDetailsImpl userDetails);
}
