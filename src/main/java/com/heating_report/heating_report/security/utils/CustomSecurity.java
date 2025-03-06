package com.heating_report.heating_report.security.utils;

import com.heating_report.heating_report.accouting.dao.UserRepository;
import com.heating_report.heating_report.accouting.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class CustomSecurity {

    private final UserRepository userRepository;

    public boolean hasUserAccessToUserId(String userName, String userId) {
        UserAccount userAccount = userRepository.findByUsernameIgnoreCase(userName).orElse(null);
        return userAccount != null && userAccount.getUserId().equals(userId);
    }

    public boolean isUserAdmin(Supplier<Authentication> authentication) {
        return authentication.get().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
    }
}