package com.tp.proyecto1.utils.excelExporter;

class ExporterException extends RuntimeException {
	ExporterException(String message) {
		super(message);
	}

	ExporterException(String message, Exception e) {
		super(message, e);
	}
}