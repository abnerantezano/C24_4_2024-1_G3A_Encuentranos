package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ClienteModel
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClienteApi {
    // Nuevo método para buscar cliente por usuario ID
    @POST("cliente/agregar")
    fun agregarCliente(@Body cliente: ClienteModel): Call<ClienteModel>
    @GET("cliente/buscar-usuario/{idCliente}")
    fun buscarClientePorUsuario(@Path("idCliente") idCliente: Int): Call<ClienteModel>

    @PUT("cliente/actualizar/{idCliente}")
    fun actualizarCliente(@Path("idCliente") idCliente: Int, @Body cliente: ClienteModel): Call<ClienteModel>

    @PATCH("cliente/{id}")
    fun actualizarDescripcionCliente(@Path("id") id: Int, @Body update: Map<String, String>): Call<Void>

}