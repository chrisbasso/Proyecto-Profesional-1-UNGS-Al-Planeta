package com.tp.proyecto1.model.clientes;


import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Interesado extends Persona{



    public Interesado() {
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interesado that = (Interesado) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(apellido, that.apellido) &&
                Objects.equals(telefono, that.telefono) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, telefono, email);
    }
}
