package com.tp.proyecto1.model.contabilidad;

import java.util.ArrayList;
import java.util.List;

public enum TipoCuenta {
	ACTIVO, PASIVO, PN, INGRESO, EGRESO;
	
	public static List<TipoCuenta> getAllTipos(){
		List<TipoCuenta> tipoCuentas = new ArrayList<TipoCuenta>();
		tipoCuentas.add(TipoCuenta.ACTIVO);
		tipoCuentas.add(TipoCuenta.PASIVO);
		tipoCuentas.add(TipoCuenta.PN);
		tipoCuentas.add(TipoCuenta.INGRESO);
		tipoCuentas.add(TipoCuenta.EGRESO);
		return tipoCuentas;
	}
}
