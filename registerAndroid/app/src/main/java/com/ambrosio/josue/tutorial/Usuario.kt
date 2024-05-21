package com.ambrosio.josue.tutorial

data class Usuario(
    val id: Int,
    val idTipo: TipoUsuario,
    val correo: String,
    val contrasena: String,
    val imageUrl: String?,
    val eliminada: Boolean
)
