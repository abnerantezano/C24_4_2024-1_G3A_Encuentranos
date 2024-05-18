package com.proyecto.encuentranos.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.CalificacionProveedorModelo;
import com.proyecto.encuentranos.modelos.CalificacionProveedorPk;
//CREANDO EL REPOSITORIO PARA CalificacionProveedorModelo
@Repository
public interface ICalificacionProveedorRepositorio extends JpaRepository<CalificacionProveedorModelo, CalificacionProveedorPk>{
	
	//ESTAMOS CONSIGUIENDO LAS CALIFICACIONES DE UN PROVEEDOR POR SU ID
	//NOS SIRVE PARA EL PROMEDIO DE CALIFICACIONES
    @Query("SELECT cp FROM CalificacionProveedorModelo cp WHERE cp.id.idProveedor = :idProveedor")
    List<CalificacionProveedorModelo> obtenerCalificacionesPorIdProveedor(@Param("idProveedor") Integer idProveedor);

}
