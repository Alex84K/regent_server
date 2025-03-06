package com.heating_report.heating_report.accouting.service;

import jakarta.servlet.http.Cookie;

public interface AuthService {

    String generateAccessToken(String username);

    String generateRefreshToken(String username);

    Cookie createAccessTokenCookie(String username);

    Cookie createRefreshToken(String username);
}
