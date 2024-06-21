package com.ambrosio.josue.tutorial.data.models

import com.proyecto.encuentranos.models.DetalleContratoModel
import java.util.Date

data class ContratoModel(
    val id: DetalleContratoModeloId,
    val idContrato: Int,
    val idCliente: ClienteModel,
    val fechaInicio: Date,
    val fechaFin: Date,
    val estado: String,
    val precioFinal: Double,
    val inicioServicio: Date,
    val finServicio: Date
)
