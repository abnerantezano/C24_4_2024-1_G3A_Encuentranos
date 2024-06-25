package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DetalleContratoApi {

    @POST("detalle-contrato/crear")
    fun detalleContratoAgregar(@Body contrato: DetalleContratoModel): Call<DetalleContratoModel>

    @GET("detalle-contrato/listar")
    fun listarDetallesContratos(): Call<List<DetalleContratoModel>>

    @GET("detalle-contrato/proveedor/{idProveedor}")
    fun obtenerDetalleContratoPorIdProveedor(@Path("idProveedor") idProveedor: Int): Call<List<DetalleContratoModel>>

    @GET("detalle-contrato/proveedor-pendientes/{idProveedor}")
    fun obtenerDetalleContratoPorProveedorYEstadoPendiente(@Path("idProveedor") idProveedor: Int): Call<List<DetalleContratoModel>>

    @GET("detalle-contrato/proveedor-aceptados/{idProveedor}")
    fun obtenerDetalleContratoPorProveedorYEstadoAceptado(@Path("idProveedor") idProveedor: Int): Call<List<DetalleContratoModel>>

    @GET("detalle-contrato/cliente/{clienteId}")
    fun obtenerDetalleContratoPorCliente(@Path("clienteId") clienteId: Int): Call<List<DetalleContratoModel>>
}
