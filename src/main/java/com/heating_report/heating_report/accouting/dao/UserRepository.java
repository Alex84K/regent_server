package com.heating_report.heating_report.accouting.dao;

import com.heating_report.heating_report.accouting.model.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserAccount, String> {
    Optional<UserAccount> findByUsernameEquals(String username);
    Optional<UserAccount> findUserByEmail(String email);

    Optional<UserAccount> findUserByUserId(String userId);
    Optional<UserAccount> deleteUserByUserId(String userId);
    Optional<UserAccount> findByUsernameIgnoreCase(String username);

    Boolean existsByUsernameIgnoreCase(String username);

    Boolean existsByEmail(String email);

}
