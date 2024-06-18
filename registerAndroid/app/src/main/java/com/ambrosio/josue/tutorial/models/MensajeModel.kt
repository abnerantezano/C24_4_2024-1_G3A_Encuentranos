package com.ambrosio.josue.tutorial.models

import java.sql.Time
import java.util.Date

data class MensajeModel(
    val idMensaje: Int,
    val idEmisor: UsuarioModel,
    val idReceptor: UsuarioModel,
    val idChat: ChatModel,
    val mensaje: String,
    val fechaEnvio: String,
    val horaEnvio: String
)