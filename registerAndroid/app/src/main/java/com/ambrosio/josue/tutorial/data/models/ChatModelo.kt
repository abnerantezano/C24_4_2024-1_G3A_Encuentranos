package com.ambrosio.josue.tutorial.data.models

import java.util.Date

data class ChatModelo(
    val idChat: Int,
    val idProveedor: ProveedorModel,
    val idCliente: ClienteModel,
    val estado: String,
    val fhCreacion: Date
)
