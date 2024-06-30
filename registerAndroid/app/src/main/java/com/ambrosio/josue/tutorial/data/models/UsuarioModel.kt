package com.ambrosio.josue.tutorial.data.models

data class UsuarioModel(
    val idUsuario: Int,
    val idTipo: TipoUsuarioModel = TipoUsuarioModel(1, ""),
    val correo: String = "",
    val contrasena: String = "",
    val imageUrl: String? = null,
    val estado: String = "",
    val fechaCreacion: String = ""
)