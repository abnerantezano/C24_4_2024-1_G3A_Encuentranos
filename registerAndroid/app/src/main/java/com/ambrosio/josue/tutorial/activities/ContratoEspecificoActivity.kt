package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import com.ambrosio.josue.tutorial.databinding.ActivityContratoEspecificoBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude

class ContratoEspecificoActivity : HeaderInclude() {
    private lateinit var binding: ActivityContratoEspecificoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContratoEspecificoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los datos del intent
        val nombreCliente = intent.getStringExtra("NOMBRE_CLIENTE")
        val nombreServicio = intent.getStringExtra("NOMBRE_SERVICIO")
        val precioActual = intent.getStringExtra("PRECIO_ACTUAL")
        val estadoServicio = intent.getStringExtra("ESTADO_SERVICIO")

        binding.tvNombreCliente.text = nombreCliente
        binding.tvNombreServicio.text = nombreServicio
        binding.tvPrecioActual.text = precioActual
        binding.tvEstadoDetalleContrato.text = estadoServicio

        setupHeader()
    }
}