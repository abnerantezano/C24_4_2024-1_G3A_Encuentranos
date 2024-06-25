package com.ambrosio.josue.tutorial.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.ActivityAceptarOcancelarContratoBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.ui.viewModels.ContratoViewModel

class AceptarOCancelarContratoActivity : HeaderInclude() {

    private lateinit var binding: ActivityAceptarOcancelarContratoBinding
    private val contratoViewModel: ContratoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAceptarOcancelarContratoBinding.inflate(layoutInflater)
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

        aceptarContrato()
        denegarContrato()
        setupHeader()
    }

    private fun aceptarContrato() {
        binding.btnAceptarContrato.setOnClickListener {
            val idContrato = intent.getIntExtra("ID_CONTRATO", 0)
            contratoViewModel.aceptarContratoProveedor(idContrato)
        }
    }

    private fun denegarContrato() {
        binding.btnDenegarContrato.setOnClickListener {
            val idContrato = intent.getIntExtra("ID_CONTRATO", 0)
            contratoViewModel.denegarContratoProveedor(idContrato)
        }
    }
}