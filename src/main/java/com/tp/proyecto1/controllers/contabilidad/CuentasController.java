package com.tp.proyecto1.controllers.contabilidad;

import org.springframework.stereotype.Controller;

import com.tp.proyecto1.views.contabilidad.CuentasView;

@Controller
public class CuentasController {

	private CuentasView cuentasView;
	
	public CuentasView getCuentasView() {
		return cuentasView;
	}
}
