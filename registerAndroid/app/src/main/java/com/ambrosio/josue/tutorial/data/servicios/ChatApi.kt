package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ChatModelo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApi {

    @GET("/chat/listar")
    fun listarChats(): Call<List<ChatModelo>>

    @GET("/chat/buscar/{id}")
    fun obtenerChatsPorId(@Path("id") id: Int): Call<ChatModelo>

    @POST("/chat/agregar")
    fun agregarChat(@Body chat: ChatModelo): Call<ChatModelo>

    @GET("/chat/{idCliente}/{idProveedor}")
    fun obtenerOcrearChat(
        @Path("idCliente") idCliente: Int,
        @Path("idProveedor") idProveedor: Int
    ): Call<ChatModelo>
}
