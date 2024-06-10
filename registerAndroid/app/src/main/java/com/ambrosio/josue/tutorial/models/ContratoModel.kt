package com.ambrosio.josue.tutorial.models

import com.proyecto.encuentranos.models.ClienteModel
import java.util.Date

data class ContratoModel(
    val idContrato: Int,
    val idCliente: ClienteModel,
    val fechaInicio: Date,
    val fechaFin: Date,
    val estado: String,
    val precioFinal: Double,
    val inicioServicio: Date,
    val finServicio: Date
)
