package com.tp.proyecto1.views.contabilidad;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.utils.FilterGridLayout;

public class ReporteCaja  extends FilterGridLayout<Asiento> {

	public ReporteCaja() {
		super(Asiento.class);
	}

}
