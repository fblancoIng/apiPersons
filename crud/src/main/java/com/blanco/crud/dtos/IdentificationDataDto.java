package com.blanco.crud.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class IdentificationDataDto {

    @NotNull(message = "The attribute documentNumber cannot be null")
    private Integer documentNumber;
    @NotNull(message = "The attribute documentType cannot be null")
    private String documentType;
    @NotNull(message = "The attribute sex cannot be null")
    @NotBlank(message = "Invalid Name: Empty sex")
    private String sex;
    @NotBlank(message = "Invalid Name: Empty country")
    @NotNull(message = "The attribute country cannot be null")
    private String country;
}
