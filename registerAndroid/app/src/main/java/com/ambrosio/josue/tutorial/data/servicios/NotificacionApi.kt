package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.NotificacionModel
import retrofit2.Call
import retrofit2.http.*


interface NotificacionApi {

    @POST("notificacion/agregar/{idCliente}/{idProveedor}/{idContrato}")
    fun agregarNotificacion(
        @Path("idCliente") idCliente: Int,
        @Path("idProveedor") idProveedor: Int,
        @Path("idContrato") idContrato: Int,
        @Body notificacion: NotificacionModel
    ): Call<NotificacionModel>
    @GET("notificacion/listar")
    fun listarNotificaciones(): Call<List<NotificacionModel>>

    @GET("notificacion/buscar/{id}")
    fun buscarNotificacionPorId(@Path("id") id: Int): Call<NotificacionModel>

    @GET("notificacion/cliente/{idCliente}")
    fun listarNotificacionesPorIdCliente(@Path("idCliente") idCliente: Int): Call<List<NotificacionModel>>

    @GET("notificacion/proveedor/{idProveedor}")
    fun listarNotificacionesPorIdProveedor(@Path("idProveedor") idProveedor: Int): Call<List<NotificacionModel>>

    @GET("notificacion/estado/{estado}")
    fun listarNotificacionesPorEstado(@Path("estado") estado: String): Call<List<NotificacionModel>>

    @GET("notificacion/tipo/{tipo}")
    fun listarNotificacionesPorTipo(@Path("tipo") tipo: String): Call<List<NotificacionModel>>

    @POST("notificacion/agregar")
    fun agregarNotificacion(@Body notificacion: NotificacionModel): Call<NotificacionModel>

    @PUT("notificacion/actualizar-estado/{id}")
    fun actualizarEstadoNotificacion(@Path("id") id: Int): Call<Void>

    @DELETE("notificacion/eliminar/{id}")
    fun eliminarNotificacion(@Path("id") id: Int): Call<Void>
}
