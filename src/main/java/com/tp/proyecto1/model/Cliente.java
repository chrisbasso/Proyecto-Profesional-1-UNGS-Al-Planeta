package com.tp.proyecto1.model;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Cliente {

	@Id
	@GeneratedValue
	private Long id;

	private String nombre;
	private String apellido;
	private String dni;
	private LocalDate fechaNacimiento;

	@OneToOne(cascade = CascadeType.ALL)
	private Domicilio domicilio;

	private String email;
	private String telefono;

	public Cliente() {
	}

	public Cliente(String nombre, String apellido, String dni, LocalDate fechaNacimiento, Domicilio domicilio, String email, String telefono) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.domicilio = domicilio;
		this.email = email;
		this.telefono = telefono;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cliente cliente = (Cliente) o;
		return Objects.equals(id, cliente.id) &&
				Objects.equals(nombre, cliente.nombre) &&
				Objects.equals(apellido, cliente.apellido) &&
				Objects.equals(dni, cliente.dni) &&
				Objects.equals(fechaNacimiento, cliente.fechaNacimiento) &&
				Objects.equals(domicilio, cliente.domicilio) &&
				Objects.equals(email, cliente.email) &&
				Objects.equals(telefono, cliente.telefono);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, apellido, dni, fechaNacimiento, domicilio, email, telefono);
	}
}
