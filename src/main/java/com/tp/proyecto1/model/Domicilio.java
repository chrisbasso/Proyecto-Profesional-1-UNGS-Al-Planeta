package com.tp.proyecto1.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Domicilio {

    @Id
    @GeneratedValue
    private Long id;

    private String calle;
    private String altura;
    private String localidad;
    private String ciudad;
    private String pais;
    private String codPostal;

    public Domicilio() {
    }

    public Domicilio(String calle, String altura, String localidad, String ciudad, String pais, String codPostal) {
        this.calle = calle;
        this.altura = altura;
        this.localidad = localidad;
        this.ciudad = ciudad;
        this.pais = pais;
        this.codPostal = codPostal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Domicilio domicilio = (Domicilio) o;
        return Objects.equals(id, domicilio.id) &&
                Objects.equals(calle, domicilio.calle) &&
                Objects.equals(altura, domicilio.altura) &&
                Objects.equals(localidad, domicilio.localidad) &&
                Objects.equals(ciudad, domicilio.ciudad) &&
                Objects.equals(pais, domicilio.pais) &&
                Objects.equals(codPostal, domicilio.codPostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, calle, altura, localidad, ciudad, pais, codPostal);
    }
}
