package com.ambrosio.josue.tutorial.data.servicios

import com.ambrosio.josue.tutorial.data.models.CalificacionModel
import com.ambrosio.josue.tutorial.data.models.DetalleCalificacionModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DetalleCalificacionApi {
    @GET("detalle-calificacion/buscar/{idProveedor}")
    fun detalleCalificacionPorIdProveedor(@Path("idProveedor") idProveedor: Int): Call<List<DetalleCalificacionModel>>

    @POST("detalle-calificacion/agregar")
    fun agregarDetalleCalificacion(@Body detalleCalificacion: DetalleCalificacionModel): Call<DetalleCalificacionModel>
}