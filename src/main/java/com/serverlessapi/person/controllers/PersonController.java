package com.serverlessapi.person.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.serverlessapi.person.dtos.PersonDTO;
import com.serverlessapi.person.models.CarEntity;
import com.serverlessapi.person.models.PersonEntity;
import com.serverlessapi.person.services.PersonService;

@RestController
@RequestMapping("/api")
public class PersonController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("person")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO postPerson(@RequestBody PersonDTO PersonDTO) {
        return personService.save(PersonDTO);
    }

    @PostMapping("persons")
    @ResponseStatus(HttpStatus.CREATED)
    public List<PersonDTO> postPersons(@RequestBody List<PersonDTO> personEntities) {
        return personService.saveAll(personEntities);
    }

    @GetMapping("persons")
    public List<PersonDTO> getPersons() {
        return personService.findAll();
    	
		/*
		 * CarEntity carEntity1 = new CarEntity("Audi", "2024", 200.00f); CarEntity
		 * carEntity2 = new CarEntity("Fiat", "2024", 300.00f); List<CarEntity> carList
		 * = new ArrayList<>(); carList.add(carEntity1); carList.add(carEntity2);
		 * 
		 * PersonEntity personEntity = new PersonEntity(); personEntity.setId(new
		 * ObjectId("1")); personEntity.setFirstName("Ajit");
		 * personEntity.setLastName("Kumar"); personEntity.setAge(38);
		 * personEntity.setCars(carList); personEntity.setCreatedAt(null);
		 * personEntity.setInsurance(true); PersonEntity personEntity2 = new
		 * PersonEntity(); personEntity2.setId(new ObjectId("2"));
		 * personEntity2.setFirstName("Ravi"); personEntity2.setLastName("Kumar");
		 * personEntity2.setAge(38); personEntity2.setCars(carList);
		 * personEntity2.setCreatedAt(null); personEntity2.setInsurance(true); PersonDTO
		 * personDTO1 = new PersonDTO(personEntity); PersonDTO personDTO2 = new
		 * PersonDTO(personEntity);
		 * 
		 * List<PersonDTO> listOfPersonDTO = Arrays.asList(personDTO1, personDTO2);
		 * return listOfPersonDTO;
		 */
    }

    @GetMapping("person/{id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable String id) {
        PersonDTO PersonDTO = personService.findOne(id);
        if (PersonDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(PersonDTO);
    }

    @GetMapping("persons/{ids}")
    public List<PersonDTO> getPersons(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return personService.findAll(listIds);
    }

    @GetMapping("persons/count")
    public Long getCount() {
        return personService.count();
    }

    @DeleteMapping("person/{id}")
    public Long deletePerson(@PathVariable String id) {
        return personService.delete(id);
    }

    @DeleteMapping("persons/{ids}")
    public Long deletePersons(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return personService.delete(listIds);
    }

    @DeleteMapping("persons")
    public Long deletePersons() {
        return personService.deleteAll();
    }

    @PutMapping("person")
    public PersonDTO putPerson(@RequestBody PersonDTO PersonDTO) {
        return personService.update(PersonDTO);
    }

    @PutMapping("persons")
    public Long putPerson(@RequestBody List<PersonDTO> personEntities) {
        return personService.update(personEntities);
    }

    @GetMapping("persons/averageAge")
    public Double averageAge() {
        return personService.getAverageAge();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
