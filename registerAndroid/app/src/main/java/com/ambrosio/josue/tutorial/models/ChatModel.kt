package com.ambrosio.josue.tutorial.models

import java.util.Date

data class ChatModel(
    val idChat: Int,
    val eliminado: Boolean,
    val fechaCreacion: Date
)