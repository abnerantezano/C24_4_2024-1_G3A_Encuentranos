package com.ambrosio.josue.tutorial.models

import com.ambrosio.josue.tutorial.models.DistritoModel
import com.ambrosio.josue.tutorial.models.UsuarioModel
import java.util.Date

data class ClienteModel(
    val idCliente: Int,
    val idUsuario: UsuarioModel,
    val idDistrito: DistritoModel,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val sexo: String,
    val dni: String,
    val celular: String,
    val fechaNacimiento: Date,
    val descripcion: String,
    val fechaRegistro: Date
)
