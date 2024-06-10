package com.ambrosio.josue.tutorial.models

data class ProveedorModel(
    val idProveedor: Int,
    val idUsuario: UsuarioModel,
    val idDistrito: DistritoModel,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val sexo: String,
    val dni: String,
    val celular: String,
    val fechaNacimiento: String,
    val calificacionPromedio: Double,
    val curriculumUrl: String,
    val fechaRegistro: String
)
