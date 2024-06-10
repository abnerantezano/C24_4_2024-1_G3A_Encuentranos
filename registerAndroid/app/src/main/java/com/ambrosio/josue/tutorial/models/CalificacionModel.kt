package com.ambrosio.josue.tutorial.models

import com.proyecto.encuentranos.models.ClienteModel
import java.util.Date

data class CalificacionModel(
    val idCalificacion: Int,
    val cliente: ClienteModel,
    val numero: Int,
    val comentario: String,
    val fechaCalificacion: Date
)
