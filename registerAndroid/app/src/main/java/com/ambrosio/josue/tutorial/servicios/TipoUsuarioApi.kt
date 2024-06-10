package com.ambrosio.josue.tutorial.servicios

import com.ambrosio.josue.tutorial.models.TipoUsuarioModel
import retrofit2.Call
import retrofit2.http.GET

interface TipoUsuarioApi {
    @GET("tipo-usuario/listar")
    fun listarTipoUsuarios(): Call<List<TipoUsuarioModel>>
}