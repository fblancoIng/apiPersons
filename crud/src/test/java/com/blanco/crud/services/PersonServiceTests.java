package com.blanco.crud.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import com.blanco.crud.domain.IdentificationData;
import com.blanco.crud.domain.Person;
import com.blanco.crud.dtos.IdentificationDataDto;
import com.blanco.crud.dtos.PersonDto;
import com.blanco.crud.dtos.StatisticsDto;
import com.blanco.crud.repositories.PersonRepository;
import com.blanco.crud.services.impl.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {

    private static final String NATIONALITY_ARGENTINA = "Argentino";
    private static final String COUNTRY_ARGENTINA = "Argentina";
    private static final String SEX_MASCULINO = "masculino";
    private static final String SEX_FEMENINO = "femenino";
    private static final String DOCUMENT_TYPE_DNI = "DNI";
    private static final Integer DOCUMENT_NUMBER_MOCK = 32312998;
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    @DisplayName("Find Exist person")
    public void findPerson() throws PersistenceException, MappingException, Exception {

	// Given
	PersonDto persondto = generatePersonDto();

	Optional<Person> personMock = generatePerson();

	// Then

	when(personRepository
		.findByIdentificationDataDocumentNumberAndIdentificationDataDocumentTypeAndIdentificationDataSexAndIdentificationDataCountry(
			persondto.getIdentificationData().getDocumentNumber(),
			persondto.getIdentificationData().getDocumentType(), persondto.getIdentificationData().getSex(),
			persondto.getIdentificationData().getCountry())).thenReturn(personMock);

	PersonDto response = personService.findPerson(persondto);

	// Assertions

	Assertions.assertEquals(response.getIdentificationData().getDocumentNumber(),
		persondto.getIdentificationData().getDocumentNumber());

	Assertions.assertEquals(response.getIdentificationData().getCountry(),
		persondto.getIdentificationData().getCountry());

	Assertions.assertEquals(response.getIdentificationData().getDocumentType(),
		persondto.getIdentificationData().getDocumentType());

	Assertions.assertEquals(response.getIdentificationData().getSex(), persondto.getIdentificationData().getSex());

    }

    @Test
    @DisplayName("find non-existent person ")
    public void findPersonNoExists() throws PersistenceException, MappingException, Exception {

	// Given
	PersonDto persondto = generatePersonDto();

	Optional<Person> personMock = Optional.empty();

	// Then

	when(personRepository
		.findByIdentificationDataDocumentNumberAndIdentificationDataDocumentTypeAndIdentificationDataSexAndIdentificationDataCountry(
			persondto.getIdentificationData().getDocumentNumber(),
			persondto.getIdentificationData().getDocumentType(), persondto.getIdentificationData().getSex(),
			persondto.getIdentificationData().getCountry())).thenReturn(personMock);

	PersonDto response = personService.findPerson(persondto);

	// Assertions

	ModelMapper mapper = new ModelMapper();

	Assertions.assertNotEquals(response, mapper.map(persondto, PersonDto.class));

    }

    @Test
    @DisplayName("save person ok")
    public void save() throws PersistenceException, MappingException, Exception {

	// Given
	PersonDto persondto = generatePersonDto();

	Optional<Person> personMock = generatePerson();

	// Then

	when(personRepository
		.findByIdentificationDataDocumentNumberAndIdentificationDataDocumentTypeAndIdentificationDataSexAndIdentificationDataCountry(
			persondto.getIdentificationData().getDocumentNumber(),
			persondto.getIdentificationData().getDocumentType(), persondto.getIdentificationData().getSex(),
			persondto.getIdentificationData().getCountry())).thenReturn(Optional.empty());

	when(personRepository.saveAndFlush(personMock.get())).thenReturn(personMock.get());

	PersonDto response = personService.savePerson(persondto);

	// Assertions

	Assertions.assertEquals(response.getIdentificationData().getDocumentNumber(),
		persondto.getIdentificationData().getDocumentNumber());

	Assertions.assertEquals(response.getIdentificationData().getCountry(),
		persondto.getIdentificationData().getCountry());

	Assertions.assertEquals(response.getIdentificationData().getDocumentType(),
		persondto.getIdentificationData().getDocumentType());

	Assertions.assertEquals(response.getIdentificationData().getSex(), persondto.getIdentificationData().getSex());

    }

    @Test
    @DisplayName("update person ok")
    public void update() throws PersistenceException, MappingException, Exception {

	// Given
	PersonDto persondto = generatePersonDto();

	Optional<Person> personMock = generatePerson();

	// Then

	when(personRepository
		.findByIdentificationDataDocumentNumberAndIdentificationDataDocumentTypeAndIdentificationDataSexAndIdentificationDataCountry(
			persondto.getIdentificationData().getDocumentNumber(),
			persondto.getIdentificationData().getDocumentType(), persondto.getIdentificationData().getSex(),
			persondto.getIdentificationData().getCountry())).thenReturn(personMock);

	when(personRepository.saveAndFlush(personMock.get())).thenReturn(personMock.get());

	PersonDto response = personService.updatePerson(persondto);

	// Assertions

	Assertions.assertEquals(response.getIdentificationData().getDocumentNumber(),
		persondto.getIdentificationData().getDocumentNumber());

	Assertions.assertEquals(response.getIdentificationData().getCountry(),
		persondto.getIdentificationData().getCountry());

	Assertions.assertEquals(response.getIdentificationData().getDocumentType(),
		persondto.getIdentificationData().getDocumentType());

	Assertions.assertEquals(response.getIdentificationData().getSex(), persondto.getIdentificationData().getSex());

    }

    @Test
    @DisplayName("Person person ok")
    public void deletePerson() throws PersistenceException, MappingException, Exception {

	// Given
	PersonDto persondto = generatePersonDto();

	Optional<Person> personMock = generatePerson();

	// Then

	when(personRepository
		.findByIdentificationDataDocumentNumberAndIdentificationDataDocumentTypeAndIdentificationDataSexAndIdentificationDataCountry(
			persondto.getIdentificationData().getDocumentNumber(),
			persondto.getIdentificationData().getDocumentType(), persondto.getIdentificationData().getSex(),
			persondto.getIdentificationData().getCountry())).thenReturn(personMock);

	Boolean response = personService.deletePerson(persondto);

	// Assertions

	Assertions.assertTrue(response);

    }

    @Test
    @DisplayName("Not delete person. Person not exist in BD")
    public void notDeletePerson() throws PersistenceException, MappingException, Exception {

	// Given
	PersonDto persondto = generatePersonDto();

	// Then

	when(personRepository
		.findByIdentificationDataDocumentNumberAndIdentificationDataDocumentTypeAndIdentificationDataSexAndIdentificationDataCountry(
			persondto.getIdentificationData().getDocumentNumber(),
			persondto.getIdentificationData().getDocumentType(), persondto.getIdentificationData().getSex(),
			persondto.getIdentificationData().getCountry())).thenReturn(Optional.empty());

	Boolean response = personService.deletePerson(persondto);

	// Assertions

	Assertions.assertFalse(response);

    }

    @Test
    @DisplayName("person exist in BD")
    public void notSave() throws PersistenceException, MappingException, Exception {

	// Given
	PersonDto persondto = generatePersonDto();

	Optional<Person> personMock = generatePerson();

	// Then

	when(personRepository
		.findByIdentificationDataDocumentNumberAndIdentificationDataDocumentTypeAndIdentificationDataSexAndIdentificationDataCountry(
			persondto.getIdentificationData().getDocumentNumber(),
			persondto.getIdentificationData().getDocumentType(), persondto.getIdentificationData().getSex(),
			persondto.getIdentificationData().getCountry())).thenReturn(personMock);

	PersonDto response = personService.savePerson(persondto);

	// Assertions

	Assertions.assertNotEquals(response, persondto);

    }

    @Test
    @DisplayName("statistics")
    public void statistics() throws PersistenceException, MappingException, Exception {

	// Given
	List<Person> persons = new ArrayList<>();
	Optional<Person> personMock = generatePerson();
	persons.add(personMock.get());

	// When

	when(personRepository.findAll()).thenReturn(persons);
	when(personRepository.getTotalOfSex(SEX_MASCULINO)).thenReturn(1);
	when(personRepository.getTotalOfSex(SEX_FEMENINO)).thenReturn(1);
	when(personRepository.getTotalOfNationality(NATIONALITY_ARGENTINA)).thenReturn(1);

	StatisticsDto responseMock = new StatisticsDto();
	responseMock.setNumberOfMen(1);
	responseMock.setNumberOfWomen(1);
	responseMock.setPercentageOfNationality(
		"Total of ".concat(NATIONALITY_ARGENTINA).concat(": ").concat("100").concat("%"));

	// then
	StatisticsDto response = personService.statistics(NATIONALITY_ARGENTINA);

	// Assertions
	Assertions.assertEquals(response, responseMock);

    }

    private PersonDto generatePersonDto() {
	PersonDto persondto = new PersonDto();
	persondto.setAge(18);
	persondto.setSurname("Test");
	IdentificationDataDto identificationDataDto = new IdentificationDataDto();
	identificationDataDto.setCountry(COUNTRY_ARGENTINA);
	identificationDataDto.setDocumentNumber(DOCUMENT_NUMBER_MOCK);
	identificationDataDto.setDocumentType(DOCUMENT_TYPE_DNI);
	identificationDataDto.setSex(SEX_MASCULINO);
	persondto.setIdentificationData(identificationDataDto);
	return persondto;
    }

    private Optional<Person> generatePerson() {
	Person person = new Person();

	IdentificationData identificationData = new IdentificationData();
	identificationData.setCountry(COUNTRY_ARGENTINA);
	identificationData.setDocumentNumber(DOCUMENT_NUMBER_MOCK);
	identificationData.setDocumentType(DOCUMENT_TYPE_DNI);
	identificationData.setSex(SEX_MASCULINO);
	person.setIdentificationData(identificationData);
	person.setAge(18);
	person.setSurname("Test");

	return Optional.of(person);
    }

}
