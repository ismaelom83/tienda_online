package com.proyecto_tienda.excepcion;

public class CustomeFieldValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8537111628344721720L;
	
	private String fieldName;
	
	public CustomeFieldValidationException(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}

}
