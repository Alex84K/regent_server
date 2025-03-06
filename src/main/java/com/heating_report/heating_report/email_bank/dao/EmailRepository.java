package com.heating_report.heating_report.email_bank.dao;

import com.heating_report.heating_report.email_bank.model.Email;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepository extends MongoRepository<Email, String> {
    Email getEmailByUserId(String userId);
}
