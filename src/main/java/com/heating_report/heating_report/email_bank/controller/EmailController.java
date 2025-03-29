package com.heating_report.heating_report.email_bank.controller;

import com.heating_report.heating_report.accouting.dao.UserRepository;
import com.heating_report.heating_report.accouting.dto.UserDto;
import com.heating_report.heating_report.accouting.service.UserAccountService;
import com.heating_report.heating_report.email_bank.dao.EmailRepository;
import com.heating_report.heating_report.email_bank.dto.EmailDto;
import com.heating_report.heating_report.email_bank.dto.NewEmailDto;
import com.heating_report.heating_report.email_bank.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://regent.shk.solutions") // Разрешить запросы с этого домена
@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private final EmailService emailService;
    private final UserAccountService accountService;

    @PostMapping("")
    public EmailDto newEmail(@RequestBody NewEmailDto emailDto) {
        return emailService.newEmail(emailDto);
    }

    @GetMapping("/{emailId}")
    public EmailDto getEmailById(@PathVariable String emailId) {
        return emailService.getEmailById(emailId);
    }

    @GetMapping("")
    public Iterable<EmailDto> getAllEmail() {
        return emailService.getAllEmail();
    }

    @GetMapping("/users/{email}")
    public UserDto findUserByEmail(@PathVariable String email){
        return accountService.findUserByEmail(email);
    }


    @PutMapping("")
    public EmailDto editEmailById(String emailId, NewEmailDto emailDto) {
        return null;
    }

    @DeleteMapping("")
    public EmailDto removeEmailById(String emailId) {
        return null;
    }
}
