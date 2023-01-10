package com.blanco.crud.services;

import javax.persistence.PersistenceException;

import org.modelmapper.MappingException;

import com.blanco.crud.dtos.PersonDto;
import com.blanco.crud.dtos.StatisticsDto;

/**
 * 
 * @author Federico Blanco
 * 
 * 
 *         interface where functionalities are defined on the Person relational
 *         model
 *
 */
public interface PersonService {

    public PersonDto findPerson(PersonDto personDto) throws PersistenceException, MappingException, Exception;

    public PersonDto savePerson(PersonDto personDto) throws PersistenceException, MappingException, Exception;

    public PersonDto updatePerson(PersonDto personDto) throws PersistenceException, MappingException, Exception;

    public boolean deletePerson(PersonDto personDto) throws PersistenceException, Exception;

    public StatisticsDto statistics(String nationality) throws PersistenceException, Exception;

}
