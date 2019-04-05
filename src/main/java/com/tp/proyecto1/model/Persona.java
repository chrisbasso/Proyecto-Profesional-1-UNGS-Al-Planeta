package com.tp.proyecto1.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Persona {

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;
	private String lastName;
	private String dni;
	private String age;

	protected Persona() {
	}

	public Persona(String firstName, String lastName, String dni, String age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dni = dni;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Persona{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", dni='" + dni + '\'' +
				", age='" + age + '\'' +
				'}';
	}
}
