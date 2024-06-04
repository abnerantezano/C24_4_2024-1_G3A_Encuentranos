package com.proyecto.encuentranos.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.CalificacionModelo;
import com.proyecto.encuentranos.modelos.DetalleCalificacionModelo;
//CREANDO EL REPOSITORIO PARA CalificacionModelo
@Repository
public interface ICalificacionRepositorio extends JpaRepository<CalificacionModelo, Integer> {
	//ESTAMOS CONSIGUIENDO LAS CALIFICACIONES DE UN PROVEEDOR POR SU ID
	//NOS SIRVE PARA EL PROMEDIO DE CALIFICACIONES
    @Query("SELECT dc FROM DetalleCalificacionModelo dc WHERE dc.idProveedor.idProveedor = :idProveedor")
    List<DetalleCalificacionModelo> obtenerCalificacionesPorIdProveedor(@Param("idProveedor") Integer idProveedor);
}
