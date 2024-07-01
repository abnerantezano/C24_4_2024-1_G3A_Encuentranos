package com.ambrosio.josue.tutorial.data.models

data class DetalleContratoModel(
    val id: DetalleContratoModeloId,
    val idProveedor: ProveedorModel,
    val idServicio: ServicioModel,
    val idContrato: ContratoModel,
    val precioActual: Double
)
