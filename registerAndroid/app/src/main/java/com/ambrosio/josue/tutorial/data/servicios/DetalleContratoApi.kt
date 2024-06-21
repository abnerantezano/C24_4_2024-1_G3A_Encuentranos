package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel
import com.proyecto.encuentranos.models.DetalleContratoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetalleContratoApi {
    @GET("detalle-contrato/listar")
    fun listarDetallesContratos(): Call<List<DetalleContratoModel>>

    @GET("detalle-contrato/proveedor/{idProveedor}")
    fun obtenerDetalleContratoPorIdProveedor(@Path("idProveedor") idContrato: Int): Call<List<DetalleContratoModel>>

}