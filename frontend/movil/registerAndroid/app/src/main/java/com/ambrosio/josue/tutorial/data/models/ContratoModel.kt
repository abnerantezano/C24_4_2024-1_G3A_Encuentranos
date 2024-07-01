package com.ambrosio.josue.tutorial.data.models

import java.util.Date

data class ContratoModel(
    val idContrato: Int = 0,
    val idCliente: ClienteModel = ClienteModel(1),
    val fechaInicio: String ="",
    val fechaFin: String = "",
    val estado: String = "",
    val precioFinal: Double= 0.0,
    val hiServicio: String = "",
    val hfServicio: String ="",
    val fhCreacion: String = "",
)
