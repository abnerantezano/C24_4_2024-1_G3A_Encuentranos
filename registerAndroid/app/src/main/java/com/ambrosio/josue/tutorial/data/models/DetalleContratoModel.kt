package com.proyecto.encuentranos.models

import com.ambrosio.josue.tutorial.data.models.ContratoModel
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.data.models.ServicioModel

data class DetalleContratoModel(
    val idProveedor: ProveedorModel,
    val idServicio: ServicioModel,
    val idContrato: ContratoModel,
    val precioActual: Double
)
