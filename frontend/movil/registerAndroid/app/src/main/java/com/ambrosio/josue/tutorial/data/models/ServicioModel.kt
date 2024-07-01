package com.ambrosio.josue.tutorial.data.models

import java.util.Date

data class ServicioModel(
    val idServicio: Int,
    val nombre: String = "",
    val descripcion: String = "",
    val imagenUrl: String = ""
)
