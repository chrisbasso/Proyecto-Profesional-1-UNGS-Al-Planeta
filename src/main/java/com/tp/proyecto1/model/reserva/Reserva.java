package com.tp.proyecto1.model.reserva;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;

@Entity
public class Reserva {

		@Id
		@GeneratedValue
		private Long id;

		@OneToOne(cascade = CascadeType.ALL)
		private Viaje viaje;
		@OneToOne(cascade = CascadeType.ALL)
		private Cliente cliente;
		
		public Reserva(Long id, Viaje viaje, Cliente cliente) {
			this.id = id;
			this.viaje = viaje;
			this.cliente = cliente;
		}
		
		public Reserva() {			
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Viaje getViaje() {
			return viaje;
		}

		public void setViaje(Viaje viaje) {
			this.viaje = viaje;
		}

		public Cliente getCliente() {
			return cliente;
		}

		public void setCliente(Cliente cliente) {
			this.cliente = cliente;
		}

		
}
