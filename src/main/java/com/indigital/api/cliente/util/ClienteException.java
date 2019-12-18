package com.indigital.api.cliente.util;

public class ClienteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ClienteException(String mensaje) {
		super(mensaje);
	}
	
	public ClienteException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	} 
	
}
