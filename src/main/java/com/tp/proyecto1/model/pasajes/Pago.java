package com.tp.proyecto1.model.pasajes;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pago {

	@Id
	@GeneratedValue
	private Long id;	
	@ManyToOne
	private Transaccion transaccion;
	@OneToOne(fetch = FetchType.EAGER)
	private FormaDePago formaDePago;
	private Double importe;
	private LocalDate fechaDePago;
	private Integer puntosUsados;

	public Pago() {		
	}
	
	public Pago(Transaccion transaccion , FormaDePago formaDePago, Double importe, LocalDate fechaDePago) {
		this.transaccion = transaccion;
		this.formaDePago = formaDePago;
		this.importe = importe;
		this.fechaDePago = fechaDePago;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Transaccion getTransaccion () {
		return transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	public FormaDePago getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(FormaDePago formaDePago) {
		this.formaDePago = formaDePago;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getFechaDePago() {
		return fechaDePago;
	}

	public void setFechaDePago(LocalDate fechaDePago) {
		this.fechaDePago = fechaDePago;
	}

	public Integer getPuntosUsados() {
		return puntosUsados;
	}

	public void setPuntosUsados(Integer puntosUsados) {
		this.puntosUsados = puntosUsados;
	}

}