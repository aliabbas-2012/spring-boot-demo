package com.dev.demo.user.mapper;

import com.dev.demo.security.login.payload.response.UserInfoResponse;
import com.dev.demo.security.login.security.services.UserDetailsImpl;
import com.dev.demo.user.model.User;
import com.dev.demo.user.validation.payload.CreateUpdateRequest;
import com.dev.demo.user.validation.payload.SignupRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toRegister(SignupRequest signUpRequest);

    User toUser(CreateUpdateRequest createUpdateRequest);

    UserInfoResponse toUserInfoResponse(UserDetailsImpl userDetails);
}
