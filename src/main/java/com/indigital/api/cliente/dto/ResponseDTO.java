package com.indigital.api.cliente.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ResponseDTO<T> {
	
	public String message;
	public HttpStatus status;
	public List<T> data;
	
	public ResponseDTO() {
		this.status = HttpStatus.OK;
		this.data = new ArrayList<>();
	}
	
	

	
	
	
	

}
