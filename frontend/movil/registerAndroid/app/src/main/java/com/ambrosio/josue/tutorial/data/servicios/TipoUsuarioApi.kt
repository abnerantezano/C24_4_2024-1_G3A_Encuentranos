package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.TipoUsuarioModel
import retrofit2.Call
import retrofit2.http.GET

interface TipoUsuarioApi {
    @GET("tipo-usuario/listar")
    fun listarTipoUsuarios(): Call<List<TipoUsuarioModel>>
}