package com.ambrosio.josue.tutorial.servicios

import com.ambrosio.josue.tutorial.models.ProveedorModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ProveedorApi {
    @POST("proveedor/agregar")
    fun agregarProveedor(@Body proveedor: ProveedorModel): Call<ProveedorModel>
}