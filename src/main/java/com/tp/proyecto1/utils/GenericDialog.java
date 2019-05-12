package com.tp.proyecto1.utils;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;

public class GenericDialog extends Dialog {

    public GenericDialog(String mensaje) {
        this.add(new Label(mensaje));
        this.open();
    }
}
