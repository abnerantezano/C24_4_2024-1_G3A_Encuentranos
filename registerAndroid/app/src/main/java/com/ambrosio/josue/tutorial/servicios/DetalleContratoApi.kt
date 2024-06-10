package com.ambrosio.josue.tutorial.servicios

import com.proyecto.encuentranos.models.DetalleContratoModel
import retrofit2.Call
import retrofit2.http.GET

interface DetalleContratoApi {
    @GET("detalle-contrato/listar")
    fun listarDetallesContratos(): Call<List<DetalleContratoModel>>
}