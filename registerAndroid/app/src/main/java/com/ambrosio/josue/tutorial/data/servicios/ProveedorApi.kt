package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProveedorApi {
    @POST("proveedor/agregar")
    fun agregarProveedor(@Body proveedor: ProveedorModel): Call<ProveedorModel>

    // Nuevo método para buscar proveedor por usuario ID
    @GET("proveedor/buscar-usuario/{idUsuario}")
    fun buscarProveedorPorUsuario(@Path("idUsuario") idUsuario: Int): Call<ProveedorModel>

    @GET("proveedor/buscar/{idUsuario}")
    fun obtenerDatosDeUsuarioPorId(@Path("idUsuario") idUsuario: Int): Call<ProveedorModel>

    @PUT("proveedor/actualizar/{idProveedor}")
    fun actualizarProveedor(@Path("idProveedor") idProveedor: Int, @Body proveedor: ProveedorModel): Call<ProveedorModel>

    @PATCH("proveedor/{id}")
    fun actualizarDescripcionProveedor(@Path("id") id: Int, @Body update: Map<String, String>): Call<Void>

}
