package com.tp.proyecto1.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Cliente {

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;
	private String lastName;
	private String dni;
	private String age;
	private String email;

	protected Cliente() {
	}

	public Cliente(String firstName, String lastName, String dni, String age, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dni = dni;
		this.age = age;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Cliente{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", dni='" + dni + '\'' +
				", age='" + age + '\'' +
				", email='" + email + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cliente cliente = (Cliente) o;
		return Objects.equals(id, cliente.id) &&
				Objects.equals(firstName, cliente.firstName) &&
				Objects.equals(lastName, cliente.lastName) &&
				Objects.equals(dni, cliente.dni) &&
				Objects.equals(age, cliente.age) &&
				Objects.equals(email, cliente.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, dni, age, email);
	}
}
