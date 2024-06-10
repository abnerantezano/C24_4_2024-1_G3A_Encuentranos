package com.ambrosio.josue.tutorial.servicios

import com.ambrosio.josue.tutorial.models.UsuarioModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioApi {
    @GET("usuario/listar")
    fun listarUsuarios(): Call<List<UsuarioModel>>

    @POST("usuario/agregar")
    fun agregarUsuario(@Body usuario: UsuarioModel): Call<UsuarioModel>

    @GET("usuario/verificar/{correo}")
    fun verificarUsuarioPorCorreo(@Path("correo") correo: String): Call<UsuarioModel>
}