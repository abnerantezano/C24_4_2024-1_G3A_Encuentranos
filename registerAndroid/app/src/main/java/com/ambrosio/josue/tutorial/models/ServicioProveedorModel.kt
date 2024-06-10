package com.ambrosio.josue.tutorial.models

data class ServicioProveedorModel(
    val idProveedor: ProveedorModel,
    val idServicio: ServicioModel,
    val precio: Double,
    val negociable: Boolean
)
