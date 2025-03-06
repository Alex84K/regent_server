package com.heating_report.heating_report.email_bank.service;

import com.heating_report.heating_report.accouting.dao.UserRepository;
import com.heating_report.heating_report.email_bank.dao.EmailRepository;
import com.heating_report.heating_report.email_bank.dto.EmailDto;
import com.heating_report.heating_report.email_bank.dto.NewEmailDto;
import com.heating_report.heating_report.email_bank.dto.exceptions.EmailNotFoundException;
import com.heating_report.heating_report.email_bank.model.Email;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@Service
@RequiredArgsConstructor
@Order(10)
public class EmailServiceImpl implements EmailService{

    @Autowired
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public EmailDto newEmail(NewEmailDto emailDto) {
        Email email = modelMapper.map(emailDto, Email.class);
        emailRepository.save(email);
        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    public EmailDto getEmailById(String emailId) {
        Email email = emailRepository.findById(emailId).orElseThrow(EmailNotFoundException::new);
        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    public EmailDto editEmailById(String emailId, NewEmailDto emailDto) {
        Email email = emailRepository.findById(emailId).orElseThrow(EmailNotFoundException::new);
        email = modelMapper.map(emailDto, Email.class);
        emailRepository.save(email);
        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    public EmailDto removeEmailById(String emailId) {
        Email email = emailRepository.findById(emailId).orElseThrow(EmailNotFoundException::new);
        emailRepository.delete(email);
        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    public Iterable<EmailDto> getAllEmail() {
        List<Email> emails = emailRepository.findAll();
        return emails.stream().map(e -> modelMapper.map(e, EmailDto.class)).toList();
    }

    @Override
    public Email getEmailByUserId(String userId) {
        return emailRepository.getEmailByUserId(userId);
    }
}
