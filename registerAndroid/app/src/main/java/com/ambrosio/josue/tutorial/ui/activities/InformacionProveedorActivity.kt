package com.ambrosio.josue.tutorial.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.databinding.ActivityInformacionProveedorBinding
import com.ambrosio.josue.tutorial.ui.viewModels.InformacionProveedorViewModel


class InformacionProveedorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformacionProveedorBinding
    private val viewModel: InformacionProveedorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformacionProveedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val proveedorId = intent.getIntExtra("PROVEEDOR_ID", -1)

        viewModel.proveedor.observe(this, Observer { proveedor ->
            if (proveedor != null) {
                binding.tvNombreProveedor.text = "${proveedor.nombre} ${proveedor.apellidoPaterno} ${proveedor.apellidoMaterno}"
                binding.tvCalificacionProveedor.text = proveedor.calificacionPromedio.toString()
                binding.tvCorreoProveedor.text = proveedor.idUsuario.correo
                binding.tvCelularProveedor.text = proveedor.celular
                binding.tvDescripcionProveedor.text = proveedor.descripcion

            }
        })

        viewModel.obtenerDatosProveedorPorId(proveedorId)
    }
}
