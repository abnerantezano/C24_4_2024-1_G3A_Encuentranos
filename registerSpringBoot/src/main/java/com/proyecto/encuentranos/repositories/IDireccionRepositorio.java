package com.proyecto.encuentranos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.models.DireccionModel;
@Repository

public interface IDireccionRepositorio extends JpaRepository<DireccionModel, Long>{

}
