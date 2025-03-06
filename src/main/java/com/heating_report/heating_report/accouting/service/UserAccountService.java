package com.heating_report.heating_report.accouting.service;

import com.heating_report.heating_report.accouting.dto.*;
import com.heating_report.heating_report.utils.PagedDataResponseDto;
import jakarta.servlet.http.Cookie;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserAccountService {
    UserDto registerUser(UserRegistrationDto data) throws IOException;

    UserDto loginUser(UserLoginDto data);

    UserDto deleteUserById(String userId);

    PagedDataResponseDto<UserDto> findAll(PageRequest pageRequest);

    Iterable<UserDto> findAllAll();

    UserDto updateUser(String userId, UserEditDto data) throws IOException;

    Boolean changeRoleList(String userId, String roleName, Boolean isAddRole);

    UserDto changeEmail(String userId, String password);

    //void changePassword(String userId, String password);

    void uploadAvatar(String userId, MultipartFile file);

    void removeAvatar(String userId);

    UserDto findUserById(String id);

    //UserResponseDto updateUser(String login, UserEditRequestDto userEditDto);

    void changePassword(String login, String newPassword);

    Iterable<UserDto> getAllUsers();

    List<Cookie> logoutUser();

    UserDto findUserByEmail(String email);


}
