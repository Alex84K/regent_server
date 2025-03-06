package com.heating_report.heating_report.accouting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "regents")
@AllArgsConstructor
@Getter
public class UserAccount implements UserDetails {
    @Getter
    @Id
    private String userId;
    @Setter
    private String username;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String email;
    @Setter
    private String telefon;
    @Setter
    private String password;
    @Setter
    private String image;
    private LocalDateTime dateRegistered;
    @Setter
    private LocalDateTime lastLoginTime;
    @Setter
    private Boolean status;
    @Setter
    private String roles;
    @Setter
    private boolean isEmailActivated;
    @Setter
    private String codeForEmail;


    public UserAccount() {
        this.isEmailActivated = false;
        this.image = "https://gravatar.com/avatar/0?d=retro";
        this.dateRegistered = LocalDateTime.now();
        this.roles = "USER";
    }

    public UserAccount(String username, String email, String password, String firstName, String lastName, String telefon, String tag, String lexofficeId, String dsgvoVersion, LocalDateTime dsgvoTimestamp, String companyName, String street, String houseNumber, String country, String plz, String ort, String vatId, String code, Boolean status) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = "https://gravatar.com/avatar/0?d=retro";
        this.telefon = telefon;
        this.dateRegistered = LocalDateTime.now();
        this.lastLoginTime = LocalDateTime.now();
        this.status = status;
        this.roles = "USER";
        this.codeForEmail = code;
        this.isEmailActivated = false;
    }

    public void setSetEmailActivated() {
        this.isEmailActivated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


    /////////
}
