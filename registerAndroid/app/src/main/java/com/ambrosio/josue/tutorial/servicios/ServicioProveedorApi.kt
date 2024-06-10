package com.ambrosio.josue.tutorial.servicios

import com.ambrosio.josue.tutorial.models.ServicioProveedorModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServicioProveedorApi {
    @GET("servicio-proveedor/listar")
    fun listarServiciosProveedores(): Call<List<ServicioProveedorModel>>

    @POST("servicio-proveedor/agregar")
    fun agregarServicioProveedor(@Body serviciosProveedor: List<ServicioProveedorModel>): Call<List<ServicioProveedorModel>>
}