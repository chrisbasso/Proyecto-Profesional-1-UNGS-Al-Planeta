package com.tp.proyecto1.model.eventos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Recordatorio
{
	@Id
	@GeneratedValue
	private Long id;
	

	private LocalDate fechaRecordatorio;
	private LocalTime horaRecordatorio;
	
	@ManyToOne
	private Evento evento;
	
	public Recordatorio()
	{
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Recordatorio recordatorio = (Recordatorio) o;
		return Objects.equals(id, recordatorio.id) &&
				Objects.equals(fechaRecordatorio, recordatorio.fechaRecordatorio) &&
				Objects.equals(evento, recordatorio.evento) &&
				Objects.equals(horaRecordatorio, recordatorio.horaRecordatorio);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fechaRecordatorio, horaRecordatorio);
	}
	
	public LocalDate getFechaRecordatorio()
	{
		return fechaRecordatorio;
	}
	
	public void setFechaRecordatorio(LocalDate fechaRecordatorio)
	{
		this.fechaRecordatorio = fechaRecordatorio;
	}
	
	public LocalTime getHoraRecordatorio()
	{
		return horaRecordatorio;
	}
	
	public void setHoraRecordatorio(LocalTime horaRecordatorio)
	{
		this.horaRecordatorio = horaRecordatorio;
	}

	public Evento getEvento()
	{
		return evento;
	}

	public void setEvento(Evento evento)
	{
		this.evento = evento;
	}
	
}
