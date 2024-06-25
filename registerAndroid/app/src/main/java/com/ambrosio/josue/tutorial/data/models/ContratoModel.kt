package com.ambrosio.josue.tutorial.data.models

import java.util.Date

data class ContratoModel(
    val idContrato: Int,
    val idCliente: ClienteModel,
    val fechaInicio: String,
    val fechaFin: String,
    val estado: String,
    val precioFinal: Double,
    val hiServicio: String,
    val hfServicio: String,
    val fhCreacion: String
)
