package com.ambrosio.josue.tutorial

import com.ambrosio.josue.tutorial.models.ProveedorModel
import com.ambrosio.josue.tutorial.models.ServicioModel

data class ServicioProveedor(
    val idServicio: ServicioModel,
    val idProveedor: ProveedorModel,
    val precio: Double,
    val negociable: Boolean
)
