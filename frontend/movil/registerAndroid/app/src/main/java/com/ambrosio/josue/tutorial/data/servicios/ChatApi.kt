package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ChatModel
import com.ambrosio.josue.tutorial.data.models.ChatModelo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApi {

    @GET("/chat/listar")
    fun listarChats(): Call<List<ChatModel>>

    @GET("/chat/buscar/{id}")
    fun obtenerChatsPorId(@Path("id") id: Int): Call<ChatModel>

    @GET("/chat/cliente/{idCliente}")
    fun obtenerChatsPorIdCliente(@Path("idCliente") idCliente: Int): Call<List<ChatModel>>

    @GET("/chat/proveedor/{idProveedor}")
    fun obtenerChatsPorIdProveedor(@Path("idProveedor") idProveedor: Int): Call<List<ChatModel>>

    @POST("/chat/agregar")
    fun agregarChat(@Body chat: ChatModelo): Call<ChatModel>

    @POST("/chat/{idCliente}/{idProveedor}")
    fun obtenerOcrearChat(
        @Path("idCliente") idCliente: Int,
        @Path("idProveedor") idProveedor: Int
    ): Call<ChatModel>
}
