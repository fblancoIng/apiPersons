package com.blanco.crud.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blanco.crud.domain.Contact;
import com.blanco.crud.domain.Person;
import com.blanco.crud.dtos.ContactDto;
import com.blanco.crud.dtos.PersonDto;
import com.blanco.crud.dtos.StatisticsDto;
import com.blanco.crud.repositories.PersonRepository;
import com.blanco.crud.services.PersonService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Federico Blanco
 *
 *         Service in charge of carrying out CRUD functionalities and Statistics
 *         on the relational model associated with People
 *
 */
@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private static final String SEX_MASCULINO = "masculino";
    private static final String SEX_FEMENINO = "femenino";
    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonDto findPerson(PersonDto personDto) throws PersistenceException, MappingException, Exception {

	try {
	    return findByDocumentNumberAndTypeNumberAndSexAndCountryAndConvertDto(personDto);
	} catch (PersistenceException pe) {
	    log.error(pe.getMessage());
	    throw pe;
	} catch (MappingException me) {
	    log.error(me.getMessage());
	    throw me;
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw e;
	}

    }

    @Override
    public PersonDto savePerson(PersonDto personDto) throws PersistenceException, MappingException, Exception {

	try {
	    if (findByDocumentNumberAndTypeNumberAndSexAndCountryAndConvertDto(personDto) == null) {
		ModelMapper mapper = new ModelMapper();
		Person person = personRepository.saveAndFlush(mapper.map(personDto, Person.class));
		return mapper.map(person, PersonDto.class);
	    } else
		return null;
	} catch (PersistenceException pe) {
	    log.error(pe.getMessage());
	    throw pe;
	} catch (MappingException me) {
	    log.error(me.getMessage());
	    throw me;
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw e;
	}

    }

    @Override
    public PersonDto updatePerson(PersonDto personDto) throws PersistenceException, MappingException, Exception {

	try {

	    Person person = findByDocumentNumberAndTypeNumberAndSexAndCountry(personDto);

	    if (person != null) {

		person.setAge(personDto.getAge());
		person.getIdentificationData().setCountry(personDto.getIdentificationData().getCountry());
		person.getIdentificationData().setDocumentNumber(personDto.getIdentificationData().getDocumentNumber());
		person.getIdentificationData().setDocumentType(personDto.getIdentificationData().getDocumentType());
		person.setName(personDto.getName());
		person.setNationality(personDto.getNationality());
		person.setSurname(personDto.getSurname());
		person.getIdentificationData().setSex(personDto.getIdentificationData().getSex());

		if (person.getContact() != null) {
		    List<Contact> contactUpdate = (List<Contact>) person.getContact();
		    contactUpdate.clear();
		    ArrayList<ContactDto> contactsDto = (ArrayList<ContactDto>) personDto.getContact();
		    ModelMapper mapper = new ModelMapper();
		    for (ContactDto contactDto : contactsDto) {
			contactUpdate.add(mapper.map(contactDto, Contact.class));
		    }
		    person.setContact(contactUpdate);
		}

		personRepository.saveAndFlush(person);
		return personDto;

	    } else
		return null;

	} catch (PersistenceException pe) {
	    log.error(pe.getMessage());
	    throw pe;

	} catch (MappingException me) {
	    log.error(me.getMessage());
	    throw me;
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw e;
	}

    }

    @Override
    public boolean deletePerson(PersonDto personDto) throws PersistenceException, Exception {

	try {
	    Person person = findByDocumentNumberAndTypeNumberAndSexAndCountry(personDto);

	    if (person != null) {
		ModelMapper mapper = new ModelMapper();
		personRepository.delete(mapper.map(personDto, Person.class));
		return true;
	    } else
		return false;

	} catch (PersistenceException pe) {
	    log.error(pe.getMessage());
	    throw pe;

	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw e;
	}

    }

    @Override
    public StatisticsDto statistics(String nationality) throws PersistenceException, Exception {

	StatisticsDto statitsticsDto = new StatisticsDto();
	try {
	    Integer totalPersons = ((Long) personRepository.findAll().stream().count()).intValue();
	    if (totalPersons != 0) {
		statitsticsDto.setNumberOfMen(personRepository.getTotalOfSex(SEX_MASCULINO));
		statitsticsDto.setNumberOfWomen(personRepository.getTotalOfSex(SEX_FEMENINO));
		Integer totalOfNationality = personRepository.getTotalOfNationality(nationality);
		Integer percentageOfNationality = (totalOfNationality * 100) / totalPersons;
		statitsticsDto.setPercentageOfNationality("Total of ".concat(nationality).concat(": ")
			.concat(percentageOfNationality.toString()).concat("%"));
	    }

	} catch (

	PersistenceException pe) {
	    log.error(pe.getMessage());
	    throw pe;

	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw e;
	}
	return statitsticsDto;
    }

    private PersonDto findByDocumentNumberAndTypeNumberAndSexAndCountryAndConvertDto(PersonDto personDto) {

	Optional<Person> person = personRepository
		.findByIdentificationDataDocumentNumberAndIdentificationDataDocumentTypeAndIdentificationDataSexAndIdentificationDataCountry(
			personDto.getIdentificationData().getDocumentNumber(),
			personDto.getIdentificationData().getDocumentType(), personDto.getIdentificationData().getSex(),
			personDto.getIdentificationData().getCountry());

	if (person.isPresent()) {
	    ModelMapper mapper = new ModelMapper();
	    return mapper.map(person.get(), PersonDto.class);
	}
	return null;
    }

    private Person findByDocumentNumberAndTypeNumberAndSexAndCountry(PersonDto personDto) {

	Optional<Person> person = personRepository
		.findByIdentificationDataDocumentNumberAndIdentificationDataDocumentTypeAndIdentificationDataSexAndIdentificationDataCountry(
			personDto.getIdentificationData().getDocumentNumber(),
			personDto.getIdentificationData().getDocumentType(), personDto.getIdentificationData().getSex(),
			personDto.getIdentificationData().getCountry());

	if (person.isPresent())
	    return person.get();

	return null;
    }

}
