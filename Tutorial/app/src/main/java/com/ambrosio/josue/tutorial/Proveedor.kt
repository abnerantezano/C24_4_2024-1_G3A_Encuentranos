package com.ambrosio.josue.tutorial


data class Proveedor(
    val id: Int,
    val idUsuario: Usuario,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val sexo: String,
    val dni: String,
    val celular: String,
    val fechaNacimiento: String?,
    val idDistrito: Distrito,
    val disponible: Boolean,
    val calificacionPromedio: Double
)
