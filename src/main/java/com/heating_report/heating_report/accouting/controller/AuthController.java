package com.heating_report.heating_report.accouting.controller;

import com.heating_report.heating_report.accouting.dto.UserDto;
import com.heating_report.heating_report.accouting.dto.UserLoginDto;
import com.heating_report.heating_report.accouting.dto.UserRegistrationDto;
import com.heating_report.heating_report.accouting.service.AuthService;
import com.heating_report.heating_report.accouting.service.MailServiceImpl;
import com.heating_report.heating_report.accouting.service.UserAccountService;
import com.heating_report.heating_report.utils.MessageResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UserAccountService userAccountService;
    private final AuthService authService;
    private final MailServiceImpl mailService;
    private final ModelMapper modelMapper;

    @GetMapping("/{usersId}/email/{mailpass}")
    public ResponseEntity<UserDto> emailActivated(@Valid @PathVariable String usersId, @PathVariable String mailpass, HttpServletResponse response) {
        UserDto user = userAccountService.findUserById(usersId);
        Cookie accessTokenCookie = authService.createAccessTokenCookie(user.getEmail());
        Cookie refreshTokenCookie = authService.createRefreshToken(user.getEmail());
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        userAccountService.changeEmail(usersId, mailpass);
        UserDto u = modelMapper.map(user, UserDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserRegistrationDto data, HttpServletResponse response) {
        UserDto userResponseDto = null;
        try {
            userResponseDto = userAccountService.registerUser(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.addCookie(authService.createAccessTokenCookie(userResponseDto.getUsername()));
        response.addCookie(authService.createRefreshToken(userResponseDto.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@Valid @RequestBody UserLoginDto data, HttpServletResponse response) {
        UserDto userResponseDto = userAccountService.loginUser(data);
        response.setHeader("Set-Cookie", "accessToken=yourToken; Path=/; HttpOnly; Secure; SameSite=None");

        response.addCookie(authService.createAccessTokenCookie(userResponseDto.getUsername()));
        response.addCookie(authService.createRefreshToken(userResponseDto.getUsername()));
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<MessageResponseDto> logout(HttpServletResponse response) {
        userAccountService.logoutUser().forEach(response::addCookie);
        return ResponseEntity.ok(new MessageResponseDto("Successful logout"));
    }

    /*@GetMapping("/activation")
    public UserResponseDto activateUserEmail(@NotBlank @RequestParam String key) {
        return userAccountService.activateUserEmail(key);
    }*/

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }

}
