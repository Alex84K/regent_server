package com.heating_report.heating_report.composition.dao;

import com.heating_report.heating_report.composition.model.Composition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompositionRepository extends MongoRepository<Composition, String> {
    /*Optional<Composition> findByNameEquals(String name);
    Optional<Composition> findUserById(String id);
    Optional<Composition> deleteUserByUserId(String id);*/
}
