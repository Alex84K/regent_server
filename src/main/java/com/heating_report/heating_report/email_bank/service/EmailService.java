package com.heating_report.heating_report.email_bank.service;

import com.heating_report.heating_report.email_bank.dto.EmailDto;
import com.heating_report.heating_report.email_bank.dto.NewEmailDto;
import com.heating_report.heating_report.email_bank.model.Email;

public interface EmailService {
    EmailDto newEmail(NewEmailDto emailDto);
    EmailDto getEmailById(String emailId);
    EmailDto editEmailById(String emailId, NewEmailDto emailDto);
    EmailDto removeEmailById(String emailId);
    Iterable<EmailDto> getAllEmail();
    Email getEmailByUserId(String userId);
}
