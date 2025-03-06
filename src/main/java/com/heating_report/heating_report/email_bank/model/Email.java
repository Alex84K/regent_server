package com.heating_report.heating_report.email_bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "emails")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Email {
    @Id
    private String emailId;
    private String email;
    private String userId;
    private String role;

    public Email(String email, String userId, String role) {
        this.email = email;
        this.userId = userId;
        this.role = role;
    }


}
