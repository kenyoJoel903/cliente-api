package com.indigital.api.cliente.service;

import java.util.List;
import java.util.Locale;

import com.indigital.api.cliente.dto.ClienteCompletoDTO;
import com.indigital.api.cliente.dto.ClienteDTO;
import com.indigital.api.cliente.dto.PideCliente;
import com.indigital.api.cliente.util.ClienteException;

public interface IClienteService{
	
	ClienteDTO guardarCliente(ClienteDTO clienteDTO, Locale locale) throws ClienteException;
	
	ClienteDTO actualizarCliente(ClienteDTO clienteDTO, Locale locale) throws ClienteException;
	
	List<ClienteDTO> listarTodos(Locale locale) throws ClienteException ;
	
	PideCliente calcularEstadisticas(Locale locale) throws ClienteException;
	
	List<ClienteCompletoDTO> listarTodosClientes(Locale locale) throws ClienteException ;
	
	boolean validaCliente(ClienteDTO cliente, Locale locale) throws ClienteException;

}
