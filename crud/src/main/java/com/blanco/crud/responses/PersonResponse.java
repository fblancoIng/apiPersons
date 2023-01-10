package com.blanco.crud.responses;

import com.blanco.crud.domain.Status;
import com.blanco.crud.dtos.PersonDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PersonResponse extends Status {

    private PersonDto personDto;

}
