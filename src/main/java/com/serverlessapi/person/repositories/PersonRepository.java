package com.serverlessapi.person.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.serverlessapi.person.models.PersonEntity;

@Repository
public interface PersonRepository {

    PersonEntity save(PersonEntity personEntity);

    List<PersonEntity> saveAll(List<PersonEntity> personEntities);

    List<PersonEntity> findAll();

    List<PersonEntity> findAll(List<String> ids);

    PersonEntity findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    PersonEntity update(PersonEntity personEntity);

    long update(List<PersonEntity> personEntities);

    double getAverageAge();

}
