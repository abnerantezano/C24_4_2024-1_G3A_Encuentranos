package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorPk;

@Repository
public interface IServicioProveedorRepositorio extends JpaRepository<ServicioProveedorModelo, ServicioProveedorPk>{

}
