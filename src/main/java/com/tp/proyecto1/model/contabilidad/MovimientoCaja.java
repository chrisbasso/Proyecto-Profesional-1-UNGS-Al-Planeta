package com.tp.proyecto1.model.contabilidad;

public class MovimientoCaja {
	
	private static MovimientoCaja instancia;
	
	private Long idAsiento;
	private Cabecera cabecera;
	private Posicion posicion;

	public static MovimientoCaja getInstancia(Long idAsiento, Cabecera cabecera, Posicion posicion) {
		String cuentaCaja = "100";
		
		instancia = null;
		if(posicion.getCuenta().getNumeroCuenta().toString().equals(cuentaCaja)) {
			instancia = new MovimientoCaja(idAsiento, cabecera, posicion);	
		}
		return instancia;
	}
	
	private MovimientoCaja() {
		
	}
	
	private MovimientoCaja(Long idAsiento, Cabecera cabecera, Posicion posicion) {
		this.idAsiento = idAsiento;
		this.cabecera = cabecera;
		this.posicion = posicion;
	}

	public Long getIdAsiento() {
		return idAsiento;
	}

	public void setIdAsiento(Long idAsiento) {
		this.idAsiento = idAsiento;
	}

	public Cabecera getCabecera() {
		return cabecera;
	}

	public void setCabecera(Cabecera cabecera) {
		this.cabecera = cabecera;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}
	
	public Long getIdSucursal() {
		return cabecera.getSucursal().getId();
	}
	
	public Long getIdUsuario() {
		return cabecera.getUsuario().getId();
	}
	
	public int getMes() {
		return cabecera.getFechaContabilizacion().getMonthValue();
	}
	
	public int getDia() {
		return cabecera.getFechaContabilizacion().getDayOfMonth();
	}
	
	public Double getImporte() {
		if(posicion.getDebeHaber().equals(TipoPosicion.DEBE)) {
			return posicion.getImporte();
		}else {
			return (posicion.getImporte() * -1);
		}
	}
}