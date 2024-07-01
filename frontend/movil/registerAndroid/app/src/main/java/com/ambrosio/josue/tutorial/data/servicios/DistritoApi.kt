package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.DistritoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DistritoApi {
    @GET("distrito/listar")
    fun listarDistritos(): Call<List<DistritoModel>>
}
