package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ServicioPrestadorModelo;
@Repository
public interface IServicioPrestadorRepositorio extends JpaRepository<ServicioPrestadorModelo, Long>{

}
