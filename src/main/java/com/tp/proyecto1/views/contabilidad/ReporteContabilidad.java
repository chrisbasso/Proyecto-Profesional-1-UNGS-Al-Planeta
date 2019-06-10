package com.tp.proyecto1.views.contabilidad;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.utils.FilterGridLayout;

public class ReporteContabilidad extends FilterGridLayout<Asiento> {

	public ReporteContabilidad() {
		super(Asiento.class);
	}
}