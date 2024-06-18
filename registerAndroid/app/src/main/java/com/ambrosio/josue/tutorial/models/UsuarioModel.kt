package com.ambrosio.josue.tutorial.models

data class UsuarioModel(
    val idUsuario: Int,
    val idTipo: TipoUsuarioModel = TipoUsuarioModel(1, ""),
    val correo: String = "",
    val contrasena: String = "",
    val imageUrl: String? = null,
    val activo: Boolean = true,
    val fechaCreacion: String = ""
)