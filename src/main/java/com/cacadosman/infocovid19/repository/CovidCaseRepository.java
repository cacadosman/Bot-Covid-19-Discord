package com.cacadosman.infocovid19.repository;

import com.cacadosman.infocovid19.entity.CovidCase;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CovidCaseRepository extends MongoRepository<CovidCase, String> {
    CovidCase findByCountry(String country);
}
