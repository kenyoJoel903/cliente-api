package com.indigital.api.cliente.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.indigital.api.cliente.util.Utilitario;

public class ClienteCompletoDTO extends ClienteDTO{
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Utilitario.FORMATO_FECHA, timezone = "America/Bogota")
	public Date fechaProbableMuerte;

	public Date getFechaProbableMuerte() {
		return fechaProbableMuerte;
	}

	public void setFechaProbableMuerte(Date fechaProbableMuerte) {
		this.fechaProbableMuerte = fechaProbableMuerte;
	}
	
	

}
