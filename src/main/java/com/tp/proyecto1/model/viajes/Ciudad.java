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
    private Provincia provincia;

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


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	@Override
    public String toString()
    {
    	return getNombre() + ", " + provincia.getNombre() + " - " + provincia.getPais().getNombre();
    }




}
