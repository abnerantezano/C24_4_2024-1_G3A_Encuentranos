package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ContratoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContratoApi {

    @POST("contrato/crear")
    fun crearContrato(@Body contrato: ContratoModel): Call<ContratoModel>

    @GET("contrato/listar")
    fun listarContratos(): Call<List<ContratoModel>>

    @GET("contrato/listar/cliente/{idCliente}")
    fun listarContratoPorIdContrato(@Path("idCliente") idCliente: Int): Call<ContratoModel>

    @PUT("contrato/aceptar-proveedor")
    fun aceptarContratoProveedor(@Body contrato: ContratoModel): Call<ContratoModel>

    @PUT("contrato/denegar-proveedor")
    fun denegarContratoProveedor(@Body contrato: ContratoModel): Call<ContratoModel>
}