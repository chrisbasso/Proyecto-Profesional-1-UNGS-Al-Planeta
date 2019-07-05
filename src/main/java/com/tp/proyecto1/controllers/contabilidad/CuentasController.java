package com.tp.proyecto1.controllers.contabilidad;

import com.tp.proyecto1.views.contabilidad.CuentasView;
import org.springframework.stereotype.Controller;

@Controller
public class CuentasController {

	private CuentasView cuentasView;
	
	public CuentasView getCuentasView() {
		return cuentasView;
	}
}
