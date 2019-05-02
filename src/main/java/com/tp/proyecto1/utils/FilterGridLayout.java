package com.tp.proyecto1.utils;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class FilterGridLayout<T> extends VerticalLayout {

    Class<T> parametricClass;
    protected Grid<T> grid;
    protected HorizontalLayout hlActions = new HorizontalLayout();
    protected HorizontalLayout hlFooter = new HorizontalLayout();

    public FilterGridLayout(Class<T> parametricClass) {
        this.parametricClass = parametricClass;
        this.grid = new Grid<>(parametricClass);
        this.add(hlActions, grid, hlFooter);
        this.grid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
        this.setSizeFull();
        this.hlActions.setWidthFull();
        this.setHorizontalComponentAlignment(Alignment.END, hlFooter);
        this.hlActions.setDefaultVerticalComponentAlignment(Alignment.END);
    }

}
