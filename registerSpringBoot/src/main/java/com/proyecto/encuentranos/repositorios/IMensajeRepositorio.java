package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.MensajeModelo;
//CREANDO EL REPOSITORIO PARA MensajeModelo
@Repository
public interface IMensajeRepositorio extends JpaRepository<MensajeModelo, Integer> {
}
