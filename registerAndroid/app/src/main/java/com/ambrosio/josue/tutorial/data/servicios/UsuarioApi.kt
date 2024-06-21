package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.UsuarioModel
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

    // Nuevo método para obtener usuario por ID
    @GET("usuario/{idUsuario}")
    fun obtenerUsuarioPorId(@Path("idUsuario") idUsuario: Int): Call<UsuarioModel>
}
