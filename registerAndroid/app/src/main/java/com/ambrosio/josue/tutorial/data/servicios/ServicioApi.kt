package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.ServicioModel
import retrofit2.Call
import retrofit2.http.GET

interface ServicioApi {
    @GET("servicio/listar")
    fun listarServicios(): Call<List<ServicioModel>>
}