package com.ambrosio.josue.tutorial.data.models

data class ServicioProveedorModel(
    val id: ServicioProveedorModeloId,
    val idProveedor: ProveedorModel = ProveedorModel(
        idProveedor = 1,
        idDistrito = DistritoModel(1)
    ),
    var idServicio: ServicioModel,
    var precio: Double = 0.0,
    val negociable: Boolean = false
)