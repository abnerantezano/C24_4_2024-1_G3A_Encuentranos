package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ActualizarContrasenaRequest
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioApi {
    @GET("usuario/listar")
    fun listarUsuarios(): Call<List<UsuarioModel>>

    @POST("usuario/agregar")
    fun agregarUsuario(@Body usuario: UsuarioModel): Call<UsuarioModel>

    @GET("usuario/verificar/{correo}")
    fun verificarUsuarioPorCorreo(@Path("correo") correo: String): Call<UsuarioModel>

    @GET("usuario/{idUsuario}")
    fun obtenerUsuarioPorId(@Path("idUsuario") idUsuario: Int): Call<UsuarioModel>

    @GET("usuario/token")
    fun obtenerDatosUsuarioPorToken(@Header("Authorization") accessToken: String): Call<UsuarioModel>

    @PUT("usuario/actualizar-contrasena/{id}")
    fun actualizarContrasena(
        @Path("id") idUsuario: Int,
        @Body contrasenas: ActualizarContrasenaRequest
    ): Call<UsuarioModel>
}
