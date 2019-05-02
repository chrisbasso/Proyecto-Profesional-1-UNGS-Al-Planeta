package com.tp.proyecto1.utils;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class FilterGridLayout extends VerticalLayout {

    protected Grid<?> grid = new Grid<>();
    protected HorizontalLayout hlActions = new HorizontalLayout();
    protected HorizontalLayout hlFooter = new HorizontalLayout();

    public FilterGridLayout() {
        this.add(hlActions, grid, hlFooter);
        this.setSizeFull();
        this.hlActions.setWidthFull();
        this.hlActions.setDefaultVerticalComponentAlignment(Alignment.END);
        this.grid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);

    }

}
