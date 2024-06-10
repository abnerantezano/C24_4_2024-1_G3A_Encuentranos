package com.ambrosio.josue.tutorial.models

data class UsuarioModel(
    val idUsuario: Int,
    val idTipo: TipoUsuarioModel,
    val correo: String,
    val contrasena: String,
    val imageUrl: String?,
    val activo: Boolean,
    val fechaCreacion: String
)
