package com.tp.proyecto1.views.eventos;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class EventosHelpView extends Dialog
{

	
	private VerticalLayout mainLayout = new VerticalLayout();
	private HorizontalLayout hlActions = new HorizontalLayout();
	private HorizontalLayout manual = new HorizontalLayout();
	private Button xBtn = new Button(VaadinIcon.CLOSE.create());
	
	public EventosHelpView()
	{
		this.setWidth("768px");
		this.setHeight("480px");
		Div div = new Div();
		div.getElement().setProperty("innerHTML","<html>\n" + 
					"\n" + 
					"<head>\n" + 
					"    <meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\">\n" + 
					"    <style type=\"text/css\">\n" + 
					"        .lst-kix_7uswxbcomv0t-6>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_7uswxbcomv0t-8>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_7uswxbcomv0t-7>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_aqidbqxgczwq-1 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_aqidbqxgczwq-2 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_aqidbqxgczwq-0 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_7uswxbcomv0t-4>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_aqidbqxgczwq-5 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_aqidbqxgczwq-6 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_aqidbqxgczwq-3 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_aqidbqxgczwq-4 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_7uswxbcomv0t-5>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_aqidbqxgczwq-4>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_7uswxbcomv0t-0>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_aqidbqxgczwq-3>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_7uswxbcomv0t-3>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_aqidbqxgczwq-0>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_aqidbqxgczwq-2>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_7uswxbcomv0t-2>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_aqidbqxgczwq-1>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_7uswxbcomv0t-1>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_7uswxbcomv0t-3 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_7uswxbcomv0t-2 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_7uswxbcomv0t-1 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_7uswxbcomv0t-0 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_7uswxbcomv0t-7 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_7uswxbcomv0t-6 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_7uswxbcomv0t-5 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_7uswxbcomv0t-4 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_aqidbqxgczwq-5>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_7uswxbcomv0t-8 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_aqidbqxgczwq-7 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_aqidbqxgczwq-6>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        ul.lst-kix_aqidbqxgczwq-8 {\n" + 
					"            list-style-type: none\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_aqidbqxgczwq-7>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        .lst-kix_aqidbqxgczwq-8>li:before {\n" + 
					"            content: \"-  \"\n" + 
					"        }\n" + 
					"\n" + 
					"        ol {\n" + 
					"            margin: 0;\n" + 
					"            padding: 0\n" + 
					"        }\n" + 
					"\n" + 
					"        table td,\n" + 
					"        table th {\n" + 
					"            padding: 0\n" + 
					"        }\n" + 
					"\n" + 
					"        .c4 {\n" + 
					"            -webkit-text-decoration-skip: none;\n" + 
					"            color: #000000;\n" + 
					"            font-weight: 400;\n" + 
					"            text-decoration: underline;\n" + 
					"            vertical-align: baseline;\n" + 
					"            text-decoration-skip-ink: none;\n" + 
					"            font-size: 12pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            font-style: normal\n" + 
					"        }\n" + 
					"\n" + 
					"        .c0 {\n" + 
					"            color: #000000;\n" + 
					"            font-weight: 400;\n" + 
					"            text-decoration: none;\n" + 
					"            vertical-align: baseline;\n" + 
					"            font-size: 11pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            font-style: normal\n" + 
					"        }\n" + 
					"\n" + 
					"        .c1 {\n" + 
					"            padding-top: 0pt;\n" + 
					"            padding-bottom: 0pt;\n" + 
					"            line-height: 1.15;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: justify;\n" + 
					"            height: 11pt\n" + 
					"        }\n" + 
					"\n" + 
					"        .c5 {\n" + 
					"            color: #000000;\n" + 
					"            font-weight: 400;\n" + 
					"            text-decoration: none;\n" + 
					"            vertical-align: baseline;\n" + 
					"            font-size: 14pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            font-style: normal\n" + 
					"        }\n" + 
					"\n" + 
					"        .c7 {\n" + 
					"            color: #000000;\n" + 
					"            font-weight: 400;\n" + 
					"            text-decoration: none;\n" + 
					"            vertical-align: baseline;\n" + 
					"            font-size: 9pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            font-style: normal\n" + 
					"        }\n" + 
					"\n" + 
					"        .c16 {\n" + 
					"            padding-top: 0pt;\n" + 
					"            padding-bottom: 0pt;\n" + 
					"            line-height: 1.15;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: left;\n" + 
					"            height: 11pt\n" + 
					"        }\n" + 
					"\n" + 
					"        .c6 {\n" + 
					"            color: #000000;\n" + 
					"            font-weight: 400;\n" + 
					"            text-decoration: none;\n" + 
					"            vertical-align: baseline;\n" + 
					"            font-size: 12pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            font-style: normal\n" + 
					"        }\n" + 
					"\n" + 
					"        .c15 {\n" + 
					"            color: #000000;\n" + 
					"            font-weight: 700;\n" + 
					"            text-decoration: none;\n" + 
					"            vertical-align: baseline;\n" + 
					"            font-size: 18pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            font-style: normal\n" + 
					"        }\n" + 
					"\n" + 
					"        .c2 {\n" + 
					"            padding-top: 0pt;\n" + 
					"            padding-bottom: 0pt;\n" + 
					"            line-height: 1.15;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: justify\n" + 
					"        }\n" + 
					"\n" + 
					"        .c10 {\n" + 
					"            color: #000000;\n" + 
					"            font-weight: 400;\n" + 
					"            text-decoration: none;\n" + 
					"            vertical-align: baseline;\n" + 
					"            font-family: \"Arial\"\n" + 
					"        }\n" + 
					"\n" + 
					"        .c14 {\n" + 
					"            text-decoration-skip-ink: none;\n" + 
					"            font-size: 12pt;\n" + 
					"            -webkit-text-decoration-skip: none;\n" + 
					"            text-decoration: underline\n" + 
					"        }\n" + 
					"\n" + 
					"        .c13 {\n" + 
					"            background-color: #ffffff;\n" + 
					"            max-width: 473.7pt;\n" + 
					"            padding: 72pt 72pt 72pt 49.6pt\n" + 
					"        }\n" + 
					"\n" + 
					"        .c11 {\n" + 
					"            padding: 0;\n" + 
					"            margin: 0\n" + 
					"        }\n" + 
					"\n" + 
					"        .c8 {\n" + 
					"            margin-left: 36pt;\n" + 
					"            padding-left: 0pt\n" + 
					"        }\n" + 
					"\n" + 
					"        .c12 {\n" + 
					"            font-size: 11pt\n" + 
					"        }\n" + 
					"\n" + 
					"        .c3 {\n" + 
					"            font-style: italic\n" + 
					"        }\n" + 
					"\n" + 
					"        .c9 {\n" + 
					"            font-size: 9pt\n" + 
					"        }\n" + 
					"\n" + 
					"        .title {\n" + 
					"            padding-top: 0pt;\n" + 
					"            color: #000000;\n" + 
					"            font-size: 26pt;\n" + 
					"            padding-bottom: 3pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            line-height: 1.15;\n" + 
					"            page-break-after: avoid;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: left\n" + 
					"        }\n" + 
					"\n" + 
					"        .subtitle {\n" + 
					"            padding-top: 0pt;\n" + 
					"            color: #666666;\n" + 
					"            font-size: 15pt;\n" + 
					"            padding-bottom: 16pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            line-height: 1.15;\n" + 
					"            page-break-after: avoid;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: left\n" + 
					"        }\n" + 
					"\n" + 
					"        li {\n" + 
					"            color: #000000;\n" + 
					"            font-size: 11pt;\n" + 
					"            font-family: \"Arial\"\n" + 
					"        }\n" + 
					"\n" + 
					"        p {\n" + 
					"            margin: 0;\n" + 
					"            color: #000000;\n" + 
					"            font-size: 11pt;\n" + 
					"            font-family: \"Arial\"\n" + 
					"        }\n" + 
					"\n" + 
					"        h1 {\n" + 
					"            padding-top: 20pt;\n" + 
					"            color: #000000;\n" + 
					"            font-size: 20pt;\n" + 
					"            padding-bottom: 6pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            line-height: 1.15;\n" + 
					"            page-break-after: avoid;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: left\n" + 
					"        }\n" + 
					"\n" + 
					"        h2 {\n" + 
					"            padding-top: 18pt;\n" + 
					"            color: #000000;\n" + 
					"            font-size: 16pt;\n" + 
					"            padding-bottom: 6pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            line-height: 1.15;\n" + 
					"            page-break-after: avoid;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: left\n" + 
					"        }\n" + 
					"\n" + 
					"        h3 {\n" + 
					"            padding-top: 16pt;\n" + 
					"            color: #434343;\n" + 
					"            font-size: 14pt;\n" + 
					"            padding-bottom: 4pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            line-height: 1.15;\n" + 
					"            page-break-after: avoid;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: left\n" + 
					"        }\n" + 
					"\n" + 
					"        h4 {\n" + 
					"            padding-top: 14pt;\n" + 
					"            color: #666666;\n" + 
					"            font-size: 12pt;\n" + 
					"            padding-bottom: 4pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            line-height: 1.15;\n" + 
					"            page-break-after: avoid;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: left\n" + 
					"        }\n" + 
					"\n" + 
					"        h5 {\n" + 
					"            padding-top: 12pt;\n" + 
					"            color: #666666;\n" + 
					"            font-size: 11pt;\n" + 
					"            padding-bottom: 4pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            line-height: 1.15;\n" + 
					"            page-break-after: avoid;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: left\n" + 
					"        }\n" + 
					"\n" + 
					"        h6 {\n" + 
					"            padding-top: 12pt;\n" + 
					"            color: #666666;\n" + 
					"            font-size: 11pt;\n" + 
					"            padding-bottom: 4pt;\n" + 
					"            font-family: \"Arial\";\n" + 
					"            line-height: 1.15;\n" + 
					"            page-break-after: avoid;\n" + 
					"            font-style: italic;\n" + 
					"            orphans: 2;\n" + 
					"            widows: 2;\n" + 
					"            text-align: left\n" + 
					"        }\n" + 
					"    </style>\n" + 
					"</head>\n" + 
					"\n" + 
					"<body class=\"c13\">\n" + 
					"    <p class=\"c2\"><span class=\"c15\">1 - Secci&oacute;n Eventos</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c15\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c5\">1.1 - Sobre evento</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c2\"><span>Un evento, en el contexto de la aplicaci&oacute;n, es toda aquella consulta o reclamo que puede\n" + 
					"            hacer un cliente o un interesado. Cada uno de estos eventos cuenta inicialmente con una fecha y hora de\n" + 
					"            creaci&oacute;n, el cliente / el interesado que la realiza, un usuario creador, un usuario asignado, una\n" + 
					"            descripci&oacute;n del por qu&eacute; de la creaci&oacute;n del evento y la naturaleza del evento (Consulta\n" + 
					"            o Reclamo). Adicionalmente, un evento podr&aacute; tener una fecha y hora de vencimiento (que ser&aacute;\n" + 
					"            usada para notificar al usuario asignado con el fin de que trate ese evento), y un registro del usuario que\n" + 
					"            cierre ese evento.</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c5\">1.2 - Manual de uso</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c5\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c14\">1.2.1 - </span><span class=\"c4\">Creaci&oacute;n de un evento:</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c6\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c0\">Campos obligatorios para la creaci&oacute;n de un evento:</span></p>\n" + 
					"    <ul class=\"c11 lst-kix_7uswxbcomv0t-0 start\">\n" + 
					"        <li class=\"c2 c8\"><span class=\"c0\">Tipo de Evento.</span></li>\n" + 
					"        <li class=\"c2 c8\"><span class=\"c0\">Persona que desea crear el evento (Cliente o Interesado).</span></li>\n" + 
					"        <li class=\"c2 c8\"><span class=\"c0\">Descripci&oacute;n.</span></li>\n" + 
					"        <li class=\"c2 c8\"><span>Prioridad.</span></li>\n" + 
					"    </ul>\n" + 
					"    <p class=\"c2\"><span\n" + 
					"            style=\"overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 631.86px; height: 398.27px;\"><img\n" + 
					"                alt=\"\" src=\"img/images/image3.png\"\n" + 
					"                style=\"width: 631.86px; height: 398.27px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);\"\n" + 
					"                title=\"\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c10 c3 c9\">Creando un reclamo usando Interesado.</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c0\">Consideraciones: Fecha y hora de vencimiento no son campos obligatorios. Pero de\n" + 
					"            agregarse o bien una fecha de vencimiento o bien una hora de vencimiento, se debe completar obligatoriamente\n" + 
					"            el otro campo. Se consider&oacute; la no obligatoriedad de este campo para poder cargar el evento\n" + 
					"            r&aacute;pidamente, sin tener que elegir impulsivamente una fecha de vencimiento sin haber analizado el\n" + 
					"            motivo de la creaci&oacute;n del evento detenidamente.</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c6\"></span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c6\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c4\">1.2.2 - Editar un evento:</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c6\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c0\">Campos editables de un evento:</span></p>\n" + 
					"    <ul class=\"c11 lst-kix_aqidbqxgczwq-0 start\">\n" + 
					"        <li class=\"c2 c8\"><span class=\"c0\">Fecha y hora de vencimiento.</span></li>\n" + 
					"        <li class=\"c2 c8\"><span class=\"c0\">Descripci&oacute;n.</span></li>\n" + 
					"        <li class=\"c2 c8\"><span class=\"c0\">Prioridad.</span></li>\n" + 
					"    </ul>\n" + 
					"    <p class=\"c2\"><span\n" + 
					"            style=\"overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 512.19px; height: 318.50px;\"><img\n" + 
					"                alt=\"\" src=\"img/images/image2.png\"\n" + 
					"                style=\"width: 512.19px; height: 318.50px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);\"\n" + 
					"                title=\"\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c10 c3 c9\">Editando un evento.</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c10 c3 c12\"></span></p>\n" + 
					"    <p class=\"c2\"><span>Consideraciones: Se decidi&oacute; dejar el campo &ldquo;</span><span\n" + 
					"            class=\"c3\">Descripci&oacute;n</span><span class=\"c0\">&rdquo; editable, para poder agregar informaci&oacute;n\n" + 
					"            del evento. El cliente puede en todo momento leer el estado de sus eventos realizados para hacer un\n" + 
					"            seguimiento del mismo.</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c6\"></span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c6\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c4\">1.2.3 - Cerrar un evento:</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c0\">Un evento se podr&aacute; cerrar en todo momento. Al cerrar un evento, no puede\n" + 
					"            volver a abrirse. Al ser cerrado, se dejar&aacute; registro de qui&eacute;n fue el usuario que\n" + 
					"            realiz&oacute; la acci&oacute;n.</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c4\">1.2.4 - Listado de eventos:</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c4\"></span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c0\">La lista de eventos que puede ver un usuario del sistema contiene los eventos que\n" + 
					"            est&aacute;n conectados a &eacute;l. Un cliente solo puede ver los eventos que &eacute;l pidi&oacute; crear;\n" + 
					"            Un vendedor, los que &eacute;l cre&oacute; por el usuario y los que le han sido asignados por su supervisor;\n" + 
					"            Un supervisor, puede ver todos los eventos de la sucursal en la que el trabaja.</span></p>\n" + 
					"    <p class=\"c2\"><span\n" + 
					"            style=\"overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 631.86px; height: 172.00px;\"><img\n" + 
					"                alt=\"\" src=\"img/images/image4.png\"\n" + 
					"                style=\"width: 631.86px; height: 172.00px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);\"\n" + 
					"                title=\"\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c9\">Lista de eventos desde la vista &ldquo;Supervisor&rdquo;</span><span\n" + 
					"            class=\"c0\">&nbsp;</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c4\">1.2.5 - Reasignaci&oacute;n de un evento:</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c2\"><span>Se podr&aacute; reasignar un evento, ingresando al evento mediante el bot&oacute;n de\n" + 
					"            &ldquo;</span><span class=\"c3\">modificar</span><span class=\"c0\">&rdquo;.</span></p>\n" + 
					"    <p class=\"c2\"><span\n" + 
					"            style=\"overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 631.86px; height: 410.67px;\"><img\n" + 
					"                alt=\"\" src=\"img/images/image1.png\"\n" + 
					"                style=\"width: 631.86px; height: 410.67px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);\"\n" + 
					"                title=\"\"></span></p>\n" + 
					"    <p class=\"c2\"><span class=\"c7\">&nbsp; &nbsp; &nbsp;Re asignando un evento.</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c2\"><span>Consideraciones: S&oacute;lo un supervisor puede realizar la reasignaci&oacute;n de un evento.\n" + 
					"            S&oacute;lo podr&aacute; asignarle el evento a un empleado de su sucursal.</span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c5\"></span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c1\"><span class=\"c0\"></span></p>\n" + 
					"    <p class=\"c16\"><span class=\"c0\"></span></p>\n" + 
					"</body>\n" + 
					"\n" + 
					"</html>");
		HorizontalLayout hlSpace = new HorizontalLayout();
		hlSpace.setWidthFull();
		this.hlActions.setWidthFull();
        this.hlActions.setDefaultVerticalComponentAlignment(Alignment.END);
        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        
        this.hlActions.add(hlSpace,xBtn);
        manual.add(div);
		mainLayout.add(hlActions,manual);
		this.add(mainLayout);
	}

	public Button getxBtn()
	{
		return xBtn;
	}

	public void setxBtn(Button xBtn)
	{
		this.xBtn = xBtn;
	}
	
}
