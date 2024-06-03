package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ContratoModelo;
//CREANDO EL REPOSITORIO PARA ContratoModelo
@Repository
public interface IContratoRepositorio extends JpaRepository<ContratoModelo, Integer> {
}
