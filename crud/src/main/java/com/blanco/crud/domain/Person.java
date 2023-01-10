package com.blanco.crud.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Federico Blanco
 * 
 *         Class in charge of representing the Person table of the data model
 *
 */
@Setter
@Getter
@Entity
@Table(name = "Person")
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private IdentificationData identificationData;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "nationality", nullable = false)
    private String nationality;
    @Column(name = "age", nullable = false)
    private Integer age;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name = "documentNumber"), @JoinColumn(name = "documentType"), @JoinColumn(name = "sex"),
	    @JoinColumn(name = "country"), })
    private List<Contact> contact;

    @Override
    public boolean equals(Object obj) {
	if ((this) == obj)
	    return true;
	if (obj == null)
	    return false;
	if (this.getClass() != obj.getClass())
	    return false;
	final Person other = (Person) obj;

	if (other.identificationData.getDocumentNumber() != this.identificationData.getDocumentNumber()
		&& other.identificationData.getDocumentType() != this.identificationData.getDocumentType()
		&& other.identificationData.getCountry() != this.identificationData.getCountry()
		&& other.identificationData.getSex() != this.identificationData.getSex())
	    return false;

	return true;
    }

}
