package com.indigital.api.cliente.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indigital.api.cliente.dto.ClienteCompletoDTO;
import com.indigital.api.cliente.dto.ClienteDTO;
import com.indigital.api.cliente.dto.PideCliente;
import com.indigital.api.cliente.dto.ResponseDTO;
import com.indigital.api.cliente.service.IClienteService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private MessageSource messageSource;
	
	@ApiOperation(value = "Registrar cliente")
	@PostMapping(value = "/createcliente", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ClienteDTO>> registrar(@RequestBody ClienteDTO clienteDTO, @RequestHeader(name ="Accept-Language", required = false, defaultValue = "es-PE") Locale locale){
		ResponseDTO<ClienteDTO> response = new ResponseDTO<>();
		try {
			clienteDTO = clienteService.guardarCliente(clienteDTO, locale);
			response.status = HttpStatus.OK;
			response.message = messageSource.getMessage("com.indigital.api.cliente.registrado", null, locale);
			response.data.add(clienteDTO);
		} catch (Exception e) {
			response.message = e.getMessage();
			response.status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualizar cliente")
	@PutMapping(value = "/updatecliente", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ClienteDTO>> actualizar(@RequestBody ClienteDTO clienteDTO, @RequestHeader(name ="Accept-Language", required = false, defaultValue = "es-PE") Locale locale){
		ResponseDTO<ClienteDTO> response = new ResponseDTO<>();
		try {
			clienteDTO = clienteService.actualizarCliente(clienteDTO, locale);
			response.status = HttpStatus.OK;
			response.message = messageSource.getMessage("com.indigital.api.cliente.modificado", null, locale);
			response.data.add(clienteDTO);
		} catch (Exception e) {
			response.message = e.getMessage();
			response.status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Listar clientes")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ClienteDTO>> listar(@RequestHeader(name ="Accept-Language", required = false, defaultValue = "es-PE") Locale locale){
		ResponseDTO<ClienteDTO> response = new ResponseDTO<>();
		try {
			response.status = HttpStatus.OK;
			response.message = "OK";
			response.data.addAll(clienteService.listarTodos(locale));
		} catch (Exception e) {
			response.message = e.getMessage();
			response.status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Calcular promedio y desviación estandar de edades")
	@GetMapping(value = "/kpideclientes" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<PideCliente>> estadisitcas(Locale locale){
		ResponseDTO<PideCliente> response = new ResponseDTO<>();
		try {
			response.status = HttpStatus.OK;
			response.message = "OK";
			response.data.add(clienteService.calcularEstadisticas(locale));
		} catch (Exception e) {
			response.message = e.getMessage();
			response.status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Listar clientes con fecha problable de muerte")
	@GetMapping(value = "/listclientes" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ClienteCompletoDTO>> listclientes(Locale locale){
		ResponseDTO<ClienteCompletoDTO> response = new ResponseDTO<>();
		try {
			response.status = HttpStatus.OK;
			response.message = "OK";
			response.data.addAll(clienteService.listarTodosClientes(locale));
		} catch (Exception e) {
			response.message = e.getMessage();
			response.status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	

}
