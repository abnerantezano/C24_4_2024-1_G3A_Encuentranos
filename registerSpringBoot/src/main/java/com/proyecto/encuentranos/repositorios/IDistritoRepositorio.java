package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.DistritoModelo;
@Repository
public interface IDistritoRepositorio extends JpaRepository<DistritoModelo, Integer>{

}
