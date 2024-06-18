package com.ambrosio.josue.tutorial.servicios

import com.ambrosio.josue.tutorial.models.MensajeModel
import retrofit2.Call
import retrofit2.http.GET

interface MensajeApi {
    @GET("mensaje/listar")
    fun listarMensajes(): Call<List<MensajeModel>>
}