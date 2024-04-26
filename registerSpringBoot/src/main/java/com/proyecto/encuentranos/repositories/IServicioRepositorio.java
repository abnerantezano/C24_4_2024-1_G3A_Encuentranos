package com.proyecto.encuentranos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.models.ServicioModel;
@Repository

public interface IServicioRepositorio extends JpaRepository<ServicioModel, Long>{

}
