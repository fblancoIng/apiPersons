package com.blanco.crud.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blanco.crud.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    public Optional<Person> findByIdentificationDataDocumentNumberAndIdentificationDataDocumentTypeAndIdentificationDataSexAndIdentificationDataCountry(
	    Integer documentNumber, String typeNumber, String sex, String country);

    @Query("SELECT COUNT(*) FROM Person p WHERE p.identificationData.sex =?1")
    public Integer getTotalOfSex(String sex);

    @Query("SELECT COUNT(*) FROM Person p WHERE p.nationality =?1 ")
    public Integer getTotalOfNationality(String nationality);
}
