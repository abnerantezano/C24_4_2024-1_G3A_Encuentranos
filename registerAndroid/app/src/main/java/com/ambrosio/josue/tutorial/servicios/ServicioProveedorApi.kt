package com.ambrosio.josue.tutorial.servicios

import com.ambrosio.josue.tutorial.models.ServicioProveedorModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServicioProveedorApi {
    @GET("servicio-proveedor/listar")
    fun listarServiciosProveedores(): Call<List<ServicioProveedorModel>>

    @POST("servicio-proveedor/agregar")
    fun agregarServicioProveedor(@Body serviciosProveedor: List<ServicioProveedorModel>): Call<Void>

    @GET("servicio-proveedor/listar/{idProveedor}")
    fun obtenerServicioProveedorPorIdProveedor(@Path("idProveedor") idProveedor: Int): Call<List<ServicioProveedorModel>>
}