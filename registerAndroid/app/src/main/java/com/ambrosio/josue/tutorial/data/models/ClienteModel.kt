package com.ambrosio.josue.tutorial.data.models

data class ClienteModel(
    val idCliente: Int,
    val idUsuario: UsuarioModel? = null,
    val idDistrito: DistritoModel? = null,
    val nombre: String? = null,
    val apellidoPaterno: String? = null,
    val apellidoMaterno: String? = null,
    val sexo: String? = null,
    val dni: String? = null,
    val celular: String? = null,
    val fechaNacimiento: String? = null,
    val descripcion: String? = null,
    val fechaRegistro: String? = null
)
