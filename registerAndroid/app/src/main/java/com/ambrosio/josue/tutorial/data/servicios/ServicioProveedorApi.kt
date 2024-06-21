package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ServicioModel
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel
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

    @GET("servicio-proveedor/buscar/{idProveedor}")
    fun obtenerServicioProveedorPorIdProveedor(@Path("idProveedor") idProveedor: Int): Call<List<ServicioProveedorModel>>

    @GET("servicio-proveedor/servicios-no-registrados/{idProveedor}")
    fun listarServiciosNoRegistrados(@Path("idProveedor") idProveedor: Int): Call<List<ServicioModel>>
}