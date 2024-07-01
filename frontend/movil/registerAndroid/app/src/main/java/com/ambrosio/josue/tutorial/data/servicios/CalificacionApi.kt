package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.CalificacionModel
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CalificacionApi {

    @POST("calificacion/agregar")
    fun agregarCalificacion(@Body calificacion: CalificacionModel): Call<CalificacionModel>

}