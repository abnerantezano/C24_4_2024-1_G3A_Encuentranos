package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ClienteModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ClienteApi {
    // Nuevo método para buscar cliente por usuario ID
    @GET("cliente/buscar-usuario/{idCliente}")
    fun buscarClientePorUsuario(@Path("idCliente") idCliente: Int): Call<ClienteModel>

}