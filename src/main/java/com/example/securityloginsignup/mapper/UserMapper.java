package com.example.securityloginsignup.mapper;

import com.example.securityloginsignup.model.dto.request.RegisterRequest;
import com.example.securityloginsignup.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "username", source = "email")
    User requestToEntity(RegisterRequest userRequest);
}
