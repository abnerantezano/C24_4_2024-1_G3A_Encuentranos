package com.ambrosio.josue.tutorial.servicios

import com.ambrosio.josue.tutorial.models.ProveedorModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProveedorApi {
    @POST("proveedor/agregar")
    fun agregarProveedor(@Body proveedor: ProveedorModel): Call<ProveedorModel>

    // Nuevo método para buscar proveedor por usuario ID
    @GET("proveedor/buscar-usuario/{idUsuario}")
    fun buscarProveedorPorUsuario(@Path("idUsuario") idUsuario: Int): Call<ProveedorModel>
}
