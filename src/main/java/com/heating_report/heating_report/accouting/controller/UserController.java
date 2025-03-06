package com.heating_report.heating_report.accouting.controller;

import com.heating_report.heating_report.accouting.dto.RolesDto;
import com.heating_report.heating_report.accouting.dto.UserDto;
import com.heating_report.heating_report.accouting.dto.UserEditDto;
import com.heating_report.heating_report.accouting.dto.exceptions.UserUnauthorizedException;
import com.heating_report.heating_report.accouting.model.UserAccount;
import com.heating_report.heating_report.accouting.service.UserAccountService;
import com.heating_report.heating_report.utils.MessageResponseDto;
import com.heating_report.heating_report.utils.PagedDataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserAccountService userAccountService;
    //private final MailServiceImpl mailService;

    @GetMapping("")
    public PagedDataResponseDto<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "25", name = "size") Integer size,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page
    ) {
        return userAccountService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal UserAccount user) {
        if (user == null) {
            throw new UserUnauthorizedException();
        }
        return userAccountService.findUserById(user.getUserId());
    }

    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable String userId) {
        return userAccountService.findUserById(userId);
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@Valid @RequestBody UserEditDto data, @PathVariable String userId) throws IOException {
        return userAccountService.updateUser(userId, data);
    }

    @DeleteMapping("/{userId}")
    public UserDto deleteUser(@PathVariable String userId) {
        return userAccountService.deleteUserById(userId);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<MessageResponseDto> changePassword(@PathVariable String userId,
                                                             @RequestHeader("X-Password")
                                                             String password) {
        userAccountService.changePassword(userId, password);
        return ResponseEntity.ok(new MessageResponseDto("Successfully changed password"));
    }

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<Void> uploadAvatar(@RequestParam("file") MultipartFile file, @PathVariable String userId) {
        userAccountService.uploadAvatar(userId, file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/avatar")
    public ResponseEntity<Void> removeAvatar(@PathVariable String userId) {
        userAccountService.removeAvatar(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/role/{role}")
    public Boolean addRole(@PathVariable String userId, @PathVariable String role) {
        try {
            return userAccountService.changeRoleList(userId, role, true);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user ID format");
        }
    }

    @DeleteMapping("/{userId}/role/{role}")
    public Boolean removeRole(@PathVariable String userId, @PathVariable String role) {
        try {
            return userAccountService.changeRoleList(userId, role, false);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user ID format");
        }
    }

    @GetMapping("/all")
    public Iterable<UserDto> getAllAllUsers() {
        return userAccountService.findAllAll();
    }

    @GetMapping("/foo")
    public String foo() {
        return "Hello world!";
    }

}
