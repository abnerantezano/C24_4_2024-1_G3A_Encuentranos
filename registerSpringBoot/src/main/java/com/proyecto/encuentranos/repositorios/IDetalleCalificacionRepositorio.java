package com.proyecto.encuentranos.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.DetalleCalificacionModelo;
import com.proyecto.encuentranos.modelos.DetalleCalificacionModeloId;
//CREANDO EL REPOSITORIO PARA DetalleCalificacionModelo
@Repository
public interface IDetalleCalificacionRepositorio extends JpaRepository<DetalleCalificacionModelo, DetalleCalificacionModeloId> {

	//ESTAMOS CONSIGUIENDO LAS CALIFICACIONES DE UN PROVEEDOR POR SU ID
	//NOS SIRVE PARA EL PROMEDIO DE CALIFICACIONES
    @Query("SELECT dc FROM DetalleCalificacionModelo dc WHERE dc.idProveedor.idProveedor = :idProveedor")
    List<DetalleCalificacionModelo> obtenerCalificacionesPorIdProveedor(@Param("idProveedor") Integer idProveedor);
}
