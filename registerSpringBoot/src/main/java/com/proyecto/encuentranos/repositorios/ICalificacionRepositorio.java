package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.CalificacionModelo;
//CREANDO EL REPOSITORIO PARA CalificacionModelo
@Repository
public interface ICalificacionRepositorio extends JpaRepository<CalificacionModelo, Integer> {
}
