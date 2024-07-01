package com.ambrosio.josue.tutorial.data.models

data class DetalleCalificacionModel(
    val id: DetalleContratoModeloId,
    val idProveedor: ProveedorModel,
    val idServicio: ServicioModel,
    val idCalificacion: CalificacionModel
)