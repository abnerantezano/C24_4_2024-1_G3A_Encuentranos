package com.ambrosio.josue.tutorial.data.models

import java.util.*

data class NotificacionModel(
    val idNotificacion: Int = 0,
    val idCliente: ClienteModel = ClienteModel(1),
    val idProveedor: ProveedorModel = ProveedorModel(1),
    val idContrato: ContratoModel = ContratoModel(1),
    val titulo: String = "",
    val mensaje: String ="",
    var estado: String = "no visto"
)
