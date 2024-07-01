package com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment

import android.os.Bundle
import com.ambrosio.josue.tutorial.databinding.ActivityContratoEspecificoBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ContratoEspecificoActivity : HeaderInclude() {
    private lateinit var binding: ActivityContratoEspecificoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContratoEspecificoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los datos del intent
        val tipoUsuario = intent.getStringExtra("TIPO_USUARIO")
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO")
        val nombreServicio = intent.getStringExtra("NOMBRE_SERVICIO")
        val precioActual = intent.getStringExtra("PRECIO_ACTUAL")
        val estadoServicio = intent.getStringExtra("ESTADO_SERVICIO")
        val fechaInicio = intent.getStringExtra("FECHA_INICIO")
        val fechaFin = intent.getStringExtra("FECHA_FIN")
        val horaInicio = intent.getStringExtra("HORA_INICIO")
        val horaFin = intent.getStringExtra("HORA_FIN")
        val imagenUrl = intent.getStringExtra("IMAGEN_URL")


        val sdfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val sdfOutput = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val fechaInicioFormateada = fechaInicio?.let { sdfInput.parse(it) }?.let { sdfOutput.format(it) }
        val fechaFinFormateada = fechaFin?.let { sdfInput.parse(it) }?.let { sdfOutput.format(it) }

        binding.tvTipoUsuario.text = tipoUsuario
        binding.tvNombreUsuario.text = nombreUsuario
        binding.tvNombreServicio.text = nombreServicio
        binding.tvPrecioActual.text = precioActual
        binding.tvEstadoDetalleContrato.text = estadoServicio
        binding.tvFechaInicio.text = "$fechaInicioFormateada - $horaInicio"
        binding.tvFechaFin.text = "$fechaFinFormateada - $horaFin"
        Picasso.get().load(imagenUrl).into(binding.imgCliente)


        setupHeader()
    }
}
