package com.proyecto.encuentranos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.encuentranos.models.PrestadorModel;
import com.proyecto.encuentranos.models.PrestadorServicioModel;

public interface IPrestadorServicioRepositorio extends JpaRepository<PrestadorServicioModel, Long>{

    @Query("SELECT ps FROM PrestadorServicioModel ps JOIN FETCH ps.prestador p JOIN FETCH ps.servicio s WHERE ps.id = :id")
    Optional<PrestadorServicioModel> findPrestadorServicioById(@Param("id") Long id);

    @Query("SELECT ps FROM PrestadorServicioModel ps JOIN FETCH ps.prestador p WHERE ps.id = :id")
    Optional<PrestadorServicioModel> findPrestadorByIdWithPrestador(@Param("id") Long id);

}
