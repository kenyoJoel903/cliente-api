package com.indigital.api.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indigital.api.cliente.model.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

}
