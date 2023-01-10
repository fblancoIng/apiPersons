package com.blanco.crud.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Federico Blanco
 * 
 *         class representing the composite primary key of the Person table
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class IdentificationData implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer documentNumber;
    private String documentType;
    private String sex;
    private String country;
}
