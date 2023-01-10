package com.blanco.crud.dtos;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PersonDto {

    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "The attribute name cannot be null")
    private String name;
    @NotBlank(message = "Invalid Name: Empty surname")
    @NotNull(message = "The attribute surname cannot be null")
    private String surname;
    @NotBlank(message = "Invalid Name: Empty nationality")
    @NotNull(message = "The attribute nationality cannot be null")
    private String nationality;
    @NotNull(message = "The attribute age cannot be null")
    @Min(value = 18, message = "Age cannot be less than 18")
    private Integer age;
    @NotNull(message = "The attribute Contact cannot be null")
    private List<ContactDto> contact;
    @NotNull(message = "The attribute identificationData cannot be null")
    private IdentificationDataDto identificationData;

    @Override
    public boolean equals(Object obj) {
	if ((this) == obj)
	    return true;
	if (obj == null)
	    return false;
	if (this.getClass() != obj.getClass())
	    return false;
	final PersonDto other = (PersonDto) obj;

	if (other.getIdentificationData().getDocumentNumber() != this.getIdentificationData().getDocumentNumber()
		&& other.getIdentificationData().getDocumentType() != this.getIdentificationData().getDocumentType()
		&& other.getIdentificationData().getCountry() != this.getIdentificationData().getCountry()
		&& other.getIdentificationData().getSex() != this.getIdentificationData().getSex())
	    return false;

	return true;
    }

}
