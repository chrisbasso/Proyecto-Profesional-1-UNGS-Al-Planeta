package com.tp.proyecto1.model.viajes;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Ciudad {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    @ManyToOne
    private Pais pais;

    public Ciudad() {
    }

    public Ciudad(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString()
    {
    	return getNombre();
    }
    
    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ciudad ciudad = (Ciudad) o;
		return
				Objects.equals(id, ciudad.id) &&
				Objects.equals(nombre, ciudad.nombre) &&
				Objects.equals(pais, ciudad.pais);
	}


}
