package com.proyecto.encuentranos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.models.ClienteModel;

@Repository
public interface IClienteRepositorio extends JpaRepository<ClienteModel, Long>{

}
