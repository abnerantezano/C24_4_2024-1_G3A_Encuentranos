package com.ambrosio.josue.tutorial.data.models

import java.util.Date

data class ChatModel(
    val idChat: Int,
    val idProveedor: ProveedorModel = ProveedorModel(1),
    val idCliente: ClienteModel = ClienteModel(1),
    val estado: String = "",
    val fhCreacion: String = "",
)