package com.ambrosio.josue.tutorial.data.models

data class ClienteModel(
    val idCliente: Int,
    val idUsuario: UsuarioModel = UsuarioModel(1),
    val idDistrito: DistritoModel = DistritoModel(1),
    val nombre: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val sexo: String = "",
    val dni: String = "",
    val celular: String = "",
    val fechaNacimiento: String = "",
    val descripcion: String = "",
    val fechaRegistro: String = "",
)
