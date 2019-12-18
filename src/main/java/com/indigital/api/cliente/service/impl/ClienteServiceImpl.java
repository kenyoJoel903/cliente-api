package com.indigital.api.cliente.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.indigital.api.cliente.dto.ClienteCompletoDTO;
import com.indigital.api.cliente.dto.ClienteDTO;
import com.indigital.api.cliente.dto.PideCliente;
import com.indigital.api.cliente.model.Cliente;
import com.indigital.api.cliente.repository.IClienteRepository;
import com.indigital.api.cliente.service.IClienteService;
import com.indigital.api.cliente.util.ClienteException;
import com.indigital.api.cliente.util.Utilitario;

@Service
@PropertySource({"classpath:application.properties" })
public class ClienteServiceImpl implements IClienteService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${com.indigital.api.cliente.tiempo_vida}")
	private int tiempoVida;

	@Override
	public ClienteDTO guardarCliente(ClienteDTO clienteDTO, Locale locale) throws ClienteException {
		try {
			if(validaCliente(clienteDTO, locale)) {
				Cliente cliente = new Cliente();
				BeanUtils.copyProperties(cliente, clienteDTO);
				cliente = clienteRepository.save(cliente);
				clienteDTO.setId(cliente.getId());
				clienteDTO.setEdad(Utilitario.calcularEdad(cliente.getFechaNacimiento()));
			}
		} catch (Exception e) {
			LOGGER.error("[ERROR] guardarCliente", e);
			throw new ClienteException(e.getMessage());
		}
		return clienteDTO;
	}


	@Override
	public ClienteDTO actualizarCliente(ClienteDTO clienteDTO, Locale locale) throws ClienteException {
		try {
			if(validaCliente(clienteDTO, locale)) {
				Optional<Cliente> opt = clienteRepository.findById(clienteDTO.getId());
				if(!opt.isPresent())
					throw new ClienteException(messageSource.getMessage("com.indigital.api.cliente.no_encontrado", null, locale));
				Cliente cliente = new Cliente();
				BeanUtils.copyProperties(cliente, clienteDTO);
				cliente = clienteRepository.save(cliente);
				clienteDTO.setId(cliente.getId());
				clienteDTO.setEdad(Utilitario.calcularEdad(cliente.getFechaNacimiento()));
			}
		} catch (Exception e) {
			LOGGER.error("[ERROR] guardarCliente", e);
			throw new ClienteException(e.getMessage());
		}
		return clienteDTO;
	}
	
	@Override
	public List<ClienteDTO> listarTodos(Locale locale) throws ClienteException {
		List<ClienteDTO> lista = new ArrayList<ClienteDTO>();
		try {
			List<Cliente> clientes = clienteRepository.findAll();
			for (Cliente cliente : clientes) {
				ClienteDTO clienteDTO = new ClienteDTO();
				BeanUtils.copyProperties(clienteDTO, cliente);
				clienteDTO.setEdad(Utilitario.calcularEdad(cliente.getFechaNacimiento()));
				lista.add(clienteDTO);
			}
		} catch (Exception e) {
			throw new ClienteException(messageSource.getMessage("com.indigital.api.cliente.listar.error", null, locale));
		}
		return lista;
	}
	
	@Override
	public PideCliente calcularEstadisticas(Locale locale) throws ClienteException {
		PideCliente pideCliente = new PideCliente();
		try {
			List<ClienteDTO> clientes = listarTodos(locale);
			if(clientes != null) {
				List<Integer> edades = clientes.stream()
						.map(ClienteDTO::getEdad)
						.collect(Collectors.toList());
				pideCliente.setPromedio(Utilitario.calcularPromedio(edades));
				pideCliente.setDesviacionEstandar(Utilitario.calcularDesviacionEstandar(edades));
			}
		} catch (Exception e) {
			throw new ClienteException(messageSource.getMessage("com.indigital.api.cliente.calcular_estadisticas.error", null, locale));
		}
		return pideCliente;
	}
	
	@Override
	public List<ClienteCompletoDTO> listarTodosClientes(Locale locale) throws ClienteException {
		List<ClienteCompletoDTO> lista = new ArrayList<>();
		try {
			List<Cliente> clientes = clienteRepository.findAll();
			for (Cliente cliente : clientes) {
				ClienteCompletoDTO clienteCompletoDTO = new ClienteCompletoDTO();
				BeanUtils.copyProperties(clienteCompletoDTO, cliente);
				clienteCompletoDTO.setEdad(Utilitario.calcularEdad(cliente.getFechaNacimiento()));
				clienteCompletoDTO.setFechaProbableMuerte(Utilitario.fechaProbableMuerte(cliente.getFechaNacimiento(), tiempoVida));
				lista.add(clienteCompletoDTO);
			}
		} catch (Exception e) {
			throw new ClienteException(messageSource.getMessage("com.indigital.api.cliente.listar.error", null, locale));
		}
		return lista;
	}


	@Override
	public boolean validaCliente(ClienteDTO cliente, Locale locale) throws ClienteException {
		if(cliente.getNombre() == null) 
			throw new ClienteException(messageSource.getMessage("com.indigital.api.cliente.nombre", null,locale));
		if(cliente.getApellido() == null) 
			throw new ClienteException(messageSource.getMessage("com.indigital.api.cliente.apellido", null,locale));
		if(cliente.getFechaNacimiento() == null) 
			throw new ClienteException(messageSource.getMessage("com.indigital.api.cliente.fecha_nacimiento", null,locale));
		return true;
	}


	


	


	








	

}
