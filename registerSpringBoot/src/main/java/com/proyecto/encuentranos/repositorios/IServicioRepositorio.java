package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ServicioModelo;
//CREANDO EL REPOSITORIO PARA ServicioModelo
@Repository
public interface IServicioRepositorio extends JpaRepository<ServicioModelo, Integer>{

}
