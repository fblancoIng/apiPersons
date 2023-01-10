package com.blanco.crud.controllers;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blanco.crud.dtos.IdentificationDataDto;
import com.blanco.crud.dtos.PersonDto;
import com.blanco.crud.dtos.StatisticsDto;
import com.blanco.crud.responses.PersonResponse;
import com.blanco.crud.responses.StatisticsResponse;
import com.blanco.crud.services.PersonService;
import com.blanco.crud.utils.MessageResponse;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "${cross.origin}", methods = { RequestMethod.GET, RequestMethod.POST })
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/find/{documentNumber}/{documentType}/{sex}/{country}")
    public ResponseEntity<PersonResponse> findPerson(@PathVariable Integer documentNumber,
	    @PathVariable String documentType, @PathVariable String sex, @PathVariable String country) {

	PersonDto persondto = new PersonDto();
	IdentificationDataDto identificationDataDto = new IdentificationDataDto();
	identificationDataDto.setCountry(country);
	identificationDataDto.setDocumentNumber(documentNumber);
	identificationDataDto.setDocumentType(documentType);
	identificationDataDto.setSex(sex);
	persondto.setIdentificationData(identificationDataDto);

	PersonResponse response = null;
	try {
	    PersonDto person = personService.findPerson(persondto);

	    if (person != null)
		response = generateResponse(person, response, MessageResponse.SUCCESS.getDescription(),
			MessageResponse.SUCCESS.getCode());
	    else {
		response = generateResponse(person, response, MessageResponse.PERSON_NO_EXIST_IN_BD.getDescription(),
			MessageResponse.PERSON_NO_EXIST_IN_BD.getCode());
	    }

	} catch (MappingException me) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, me,
		    MessageResponse.MAP_ENTITY_ERROR.getCode(), MessageResponse.MAP_ENTITY_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}

	catch (PersistenceException pe) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, pe,
		    MessageResponse.BD_ERROR.getCode(), MessageResponse.BD_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}

	catch (Exception e) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, e,
		    MessageResponse.INTERNAL_ERROR.getCode(), MessageResponse.INTERNAL_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<PersonResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<PersonResponse> savePerson(@Valid @RequestBody PersonDto personDto) {

	PersonResponse response = null;
	try {
	    PersonDto person = personService.savePerson(personDto);

	    if (person != null)
		response = generateResponse(person, response, MessageResponse.SUCCESS.getDescription(),
			MessageResponse.SUCCESS.getCode());
	    else {
		response = generateResponse(person, response, MessageResponse.EXIST_PERSON_IN_BD.getDescription(),
			MessageResponse.EXIST_PERSON_IN_BD.getCode());
	    }

	} catch (MappingException me) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, me,
		    MessageResponse.MAP_ENTITY_ERROR.getCode(), MessageResponse.MAP_ENTITY_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}

	catch (PersistenceException pe) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, pe,
		    MessageResponse.BD_ERROR.getCode(), MessageResponse.BD_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}

	catch (Exception e) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, e,
		    MessageResponse.INTERNAL_ERROR.getCode(), MessageResponse.INTERNAL_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<PersonResponse>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PersonResponse> updatePerson(@Valid @RequestBody PersonDto personDto) {

	PersonResponse response = null;
	try {
	    PersonDto person = personService.updatePerson(personDto);

	    if (person != null)
		response = generateResponse(person, response, MessageResponse.SUCCESS.getDescription(),
			MessageResponse.SUCCESS.getCode());
	    else {
		response = generateResponse(person, response, MessageResponse.PERSON_NO_EXIST_IN_BD.getDescription(),
			MessageResponse.PERSON_NO_EXIST_IN_BD.getCode());
	    }

	} catch (PersistenceException pe) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, pe,
		    MessageResponse.BD_ERROR.getCode(), MessageResponse.BD_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	} catch (MappingException me) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, me,
		    MessageResponse.MAP_ENTITY_ERROR.getCode(), MessageResponse.MAP_ENTITY_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}

	catch (Exception e) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, e,
		    MessageResponse.INTERNAL_ERROR.getCode(), MessageResponse.INTERNAL_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<PersonResponse>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<PersonResponse> deletePerson(@Valid @RequestBody PersonDto personDto) {

	PersonResponse response = null;
	try {

	    response = (personService.deletePerson(personDto))
		    ? generateResponse(null, response, MessageResponse.SUCCESS.getDescription(),
			    MessageResponse.SUCCESS.getCode())
		    : generateResponse(null, response, MessageResponse.PERSON_NO_EXIST_IN_BD.getDescription(),
			    MessageResponse.PERSON_NO_EXIST_IN_BD.getCode());
	} catch (PersistenceException pe) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, pe,
		    MessageResponse.BD_ERROR.getCode(), MessageResponse.BD_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}

	catch (Exception e) {
	    return new ResponseEntity<PersonResponse>(generateErrorResponse(response, e,
		    MessageResponse.INTERNAL_ERROR.getCode(), MessageResponse.INTERNAL_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<PersonResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/statistics/{nationality}")
    public ResponseEntity<StatisticsResponse> statistics(@PathVariable String nationality) {
	StatisticsResponse response = null;
	try {

	    StatisticsDto statistics = personService.statistics(nationality);

	    response = generateStatisticsResponse(statistics, response, MessageResponse.SUCCESS.getDescription(),
		    MessageResponse.SUCCESS.getCode());

	} catch (PersistenceException pe) {
	    return new ResponseEntity<StatisticsResponse>(generateStatisticsErrorResponse(response, pe,
		    MessageResponse.BD_ERROR.getCode(), MessageResponse.BD_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}

	catch (Exception e) {
	    return new ResponseEntity<StatisticsResponse>(generateStatisticsErrorResponse(response, e,
		    MessageResponse.INTERNAL_ERROR.getCode(), MessageResponse.INTERNAL_ERROR.getDescription()),
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}

	return new ResponseEntity<StatisticsResponse>(response, HttpStatus.OK);
    }

    private PersonResponse generateResponse(PersonDto person, PersonResponse response, String description,
	    String code) {
	if (response == null)
	    response = new PersonResponse();
	response.setPersonDto(person);
	response.setDescription(description);
	response.setCode(code);

	return response;
    }

    private PersonResponse generateErrorResponse(PersonResponse response, Exception e, String code,
	    String ErrorTypeMessage) {
	response = new PersonResponse();
	response.setPersonDto(null);
	response.setCode(code);
	response.setDescription(ErrorTypeMessage);

	return response;

    }

    private StatisticsResponse generateStatisticsResponse(StatisticsDto statistics, StatisticsResponse response,
	    String description, String code) {
	if (response == null)
	    response = new StatisticsResponse();
	response.setStatisticsDto(statistics);
	response.setDescription(description);
	response.setCode(code);

	return response;
    }

    private StatisticsResponse generateStatisticsErrorResponse(StatisticsResponse response, Exception e, String code,
	    String ErrorTypeMessage) {
	response = new StatisticsResponse();
	response.setStatisticsDto(null);
	response.setCode(code);
	response.setDescription(ErrorTypeMessage);

	return response;

    }
}
