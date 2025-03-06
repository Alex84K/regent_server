package com.heating_report.heating_report.accouting.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heating_report.heating_report.accouting.dao.UserRepository;
import com.heating_report.heating_report.accouting.dto.*;
import com.heating_report.heating_report.accouting.dto.exceptions.*;
import com.heating_report.heating_report.accouting.model.UserAccount;
import com.heating_report.heating_report.email_bank.dao.EmailRepository;
import com.heating_report.heating_report.email_bank.model.Email;
import com.heating_report.heating_report.utils.PagedDataResponseDto;
import com.heating_report.heating_report.accouting.model.Role;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@CrossOrigin
@Service
@RequiredArgsConstructor
@Order(10)
public class UserAccountServiceImpl implements UserAccountService, CommandLineRunner {
    private static final Logger logger = Logger.getLogger(UserAccountServiceImpl.class.getName());

    @Autowired
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;



    @Transactional
    @Override
    public UserDto registerUser(UserRegistrationDto data) {
        if (userRepository.existsByUsernameIgnoreCase(data.getUsername())) {
            throw new UsernameAlreadyRegisteredException();
        } else if (userRepository.existsByEmail(data.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }
        UserAccount userAccount = modelMapper.map(data, UserAccount.class);
        String passwd = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(passwd);
        userRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto loginUser(UserLoginDto data) {
        Optional<UserAccount> userAccount = userRepository.findByUsernameEquals(data.getUsername());
        if (userAccount.isPresent()) {
            try {
                UserAccount user = userAccount.get();
                boolean isPasswordsMatch = passwordEncoder.matches(data.getPassword(), user.getPassword());
                if (isPasswordsMatch) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
                            passwordEncoder.encode(user.getPassword())
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return modelMapper.map(user, UserDto.class);
                } else {
                    throw new BadCredentialsOnLoginException();
                }
            } catch (BadCredentialsException e) {
                throw new BadCredentialsOnLoginException();
            }
        }
        throw new BadCredentialsOnLoginException();
    }

    @Override
    public List<Cookie> logoutUser() {
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(resetCookie("access-token"));
        cookies.add(resetCookie("refresh-token"));
        return cookies;
    }

    @Override
    public UserDto findUserById(String userId) {
        UserAccount userAccount = userRepository.findUserByUserId(userId).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Transactional
    @Override
    public UserDto deleteUserById(String userId) {
        UserAccount userAccount = userRepository.findUserByUserId(userId).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(userId);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public PagedDataResponseDto<UserDto> findAll(PageRequest pageRequest) {
        Page<UserAccount> userAccounts = userRepository.findAll(pageRequest);
        PagedDataResponseDto<UserDto> pagedDataResponseDto = new PagedDataResponseDto<>();
        Page<UserDto> pageMapped = userAccounts.map(u -> modelMapper.map(u, UserDto.class));
        pagedDataResponseDto.setData(pageMapped.getContent());
        pagedDataResponseDto.setTotalElements(pageMapped.getTotalElements());
        pagedDataResponseDto.setTotalPages(pageMapped.getTotalPages());
        pagedDataResponseDto.setCurrentPage(pageMapped.getNumber());
        return pagedDataResponseDto;
    }

    @Override
    public Iterable<UserDto> findAllAll() {
        List<UserAccount> userAccounts =  userRepository.findAll();
        List<UserDto> allUsers = userAccounts.stream().map(u -> modelMapper.map(u, UserDto.class)).toList();
        return allUsers;
    }

    @Override
    public UserDto findUserByEmail(String email) {
        UserAccount userAccount = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Transactional
    @Override
    public UserDto updateUser(String userId, UserEditDto data) throws IOException {
        UserAccount userAccount = userRepository.findUserByUserId(userId).orElseThrow(UserNotFoundException::new);
        if (data.getFirstName() != null && !data.getFirstName().equals(userAccount.getFirstName())) {
            userAccount.setFirstName(data.getFirstName());
        }
        if (data.getLastName() != null && !data.getLastName().equals(userAccount.getLastName())) {
            userAccount.setLastName(data.getLastName());
        }
        if (data.getImage() != null && !data.getImage().equals(userAccount.getImage())) {
            userAccount.setImage(data.getImage());
        }
        if (data.getTelefon() != null && !data.getTelefon().equals(userAccount.getTelefon())) {
            userAccount.setTelefon(data.getTelefon());
        }
        if (data.getStatus() != null && !data.getStatus().equals(userAccount.getStatus())) {
            userAccount.setStatus(data.getStatus());
        }
        userRepository.save(userAccount);
        Email email = emailRepository.getEmailByUserId(userId);
        email.setRole(userAccount.getRoles());
        email.setEmail(userAccount.getEmail());
        emailRepository.save(email);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public Boolean changeRoleList(String userId, String roleName, Boolean isAddRole) {
        UserAccount userAccount = userRepository.findUserByUserId(userId).orElseThrow(UserNotFoundException::new);
        roleName = roleName.toUpperCase();
        boolean res;
        try {
            if (isAddRole) {
                userAccount.setRoles(roleName);
                res = true;
            } else {
                userAccount.setRoles(Role.USER.toString());
                res = true;
            }
        } catch (Exception e) {
            throw new RoleNotFoundException();
        }
        if (res) {
            userRepository.save(userAccount);
        }
        return res;
    }
    @Override
    public void changePassword(String userId, String password) {
        if (!isValidPassword(password)) {
            throw new PasswordValidationException();
        }

        UserAccount userAccount = userRepository.findUserByUserId(userId).orElseThrow(UserNotFoundException::new);
        userAccount.setPassword(passwordEncoder.encode(password));
        userRepository.save(userAccount);
    }

    @Override
    public Iterable<UserDto> getAllUsers() {
        Iterable<UserDto> pageMapped = userRepository.findAll()
                .stream().map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
        return pageMapped;
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!$#%])[A-Za-z\\d!$#%]{8,}$");
    }

    @Override
    public void uploadAvatar(String userId, MultipartFile file) {
        String fileName = file.getOriginalFilename();
    }

    @Override
    public void removeAvatar(String userId) {

    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByUsernameIgnoreCase("admin")) {
            String password = passwordEncoder.encode("admin");
            UserAccount userAccount = new UserAccount( "admin","admin", password, "admin", "admin", "admin_telefon", "Gothem", "---", "--", LocalDateTime.now(), "--", "--", "--", "--", "--", "--", "--", "--", true);
            userAccount.setRoles(Role.ADMINISTRATOR.toString());
            userRepository.save(userAccount);
        }
    }

    private Cookie resetCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        return cookie;
    }

   @Override
    public UserDto changeEmail(String userId, String password) {
        UserAccount user = userRepository.deleteUserByUserId(userId).orElseThrow(UserNotFoundException::new);
        if(password.equals(user.getCodeForEmail())) {
            user.setSetEmailActivated();
            userRepository.save(user);
            Email email = emailRepository.getEmailByUserId(userId);
            email.setEmail(user.getEmail());
            emailRepository.save(email);
            return modelMapper.map(user, UserDto.class);
        } else {
            return null;
        }
    }
}
