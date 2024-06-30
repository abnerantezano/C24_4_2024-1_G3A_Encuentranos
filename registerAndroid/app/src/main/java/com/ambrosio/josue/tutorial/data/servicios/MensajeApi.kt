package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ChatModel
import com.ambrosio.josue.tutorial.data.models.MensajeModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MensajeApi {
    @GET("mensaje/listar")
    fun listarMensajes(): Call<List<MensajeModel>>

    @GET("/mensaje/chat/{idChat}")
    fun obtenerMensajesPorIdChat(@Path("idChat") idChat: Int): Call<List<MensajeModel>>

    @POST("mensaje/agregar")
    fun crearMensaje(@Body mensaje: MensajeModel): Call<MensajeModel>
}