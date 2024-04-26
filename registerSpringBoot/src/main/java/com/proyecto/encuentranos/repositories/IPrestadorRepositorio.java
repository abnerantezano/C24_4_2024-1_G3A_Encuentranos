package com.proyecto.encuentranos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.models.PrestadorModel;
@Repository

public interface IPrestadorRepositorio extends JpaRepository<PrestadorModel, Long>{

}
