package com.ambrosio.josue.tutorial.data.models

import java.util.Date

data class CalificacionModel(
    val idCalificacion: Int,
    val cliente: ClienteModel,
    val numero: Int,
    val comentario: String,
    val fechaCalificacion: Date
)
