package com.ambrosio.josue.tutorial

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("tipo-usuario/listar")
    fun listarTipoUsuarios(): Call<List<TipoUsuario>>

    @GET("usuario/listar")
    fun listarUsuarios(): Call<List<Usuario>>

    @GET("distrito/listar")
    fun listarDistritos(): Call<List<Distrito>>

    @POST("usuario/agregar")
    fun agregarUsuario(@Body usuario: Usuario): Call<Usuario>

    @POST("proveedor/agregar")
    fun agregarProveedor(@Body proveedor: Proveedor): Call<Proveedor>

    @GET("servicio/listar")
    fun listarServicios(): Call<List<Servicio>>
}
