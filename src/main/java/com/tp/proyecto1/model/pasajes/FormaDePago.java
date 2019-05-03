package com.tp.proyecto1.model.pasajes;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FormaDePago {

		@Id
		@GeneratedValue
		private Long id;

		private String descripcion;

		public FormaDePago() {
		}

		public FormaDePago(String descripcion) {
			this.descripcion = descripcion;
		}

		public Long getId() {
			return id;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			FormaDePago that = (FormaDePago) o;
			return Objects.equals(id, that.id) &&
					Objects.equals(descripcion, that.descripcion);
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, descripcion);
		}
	}