package com.tp.proyecto1.views.reserva;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.tp.proyecto1.utils.View;
import com.vaadin.flow.component.grid.Grid;

public class ReservaClienteView extends FilterGridLayout<Reserva> implements View 
{ 
	public ReservaClienteView(){
        super(Reserva.class);
        setGrid();
    }

	    @Override
	    public void setLayout() {
	    }
	    
	    public Grid<Reserva> getGrid()
	    {
	    	return grid;
	    }

	    @Override
	    public void setGrid() {

	        grid.setColumns("viaje.origen.provincia.pais.nombre", "viaje.origen.nombre",
	        			"viaje.destino.provincia.pais.nombre", "viaje.destino.nombre",
	        			"viaje.transporte.tipoTransporte.descripcion",
	        			"viaje.fechaSalida","importeTotal","totalPagado");
	        grid.getColumnByKey("viaje.origen.provincia.pais.nombre").setHeader("País origen");
	        grid.getColumnByKey("viaje.destino.provincia.pais.nombre").setHeader("País destino");
	        grid.getColumnByKey("viaje.origen.nombre").setHeader("Ciudad origen");
	        grid.getColumnByKey("viaje.destino.nombre").setHeader("Ciudad destino");
	        grid.getColumnByKey("viaje.transporte.tipoTransporte.descripcion").setHeader("Transporte");
	        grid.getColumnByKey("importeTotal").setHeader("Importe total");
	    }

		@Override
		public void setComponents()
		{
			// TODO Auto-generated method stub
			
		}

}
