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

        // Obtener el ID del proveedor pasado desde el intent
        val proveedorId = intent.getIntExtra("PROVEEDOR_ID", -1)

        // Observar los cambios en el ViewModel para actualizar la UI cuando se obtenga el proveedor
        viewModel.proveedor.observe(this, Observer { proveedor ->
            // Verificar si el proveedor no es nulo
            if (proveedor != null) {
                binding.tvNombreProveedor.text = "${proveedor.nombre} ${proveedor.apellidoPaterno} ${proveedor.apellidoMaterno}"
                binding.tvCalificacionProveedor.text = proveedor.calificacionPromedio.toString()
                binding.tvCorreoProveedor.text = proveedor.idUsuario.correo
                binding.tvCelularProveedor.text = proveedor.celular
                binding.tvDescripcionProveedor.text = proveedor.descripcion

            }
        })

        // Llamar al método del ViewModel para obtener los datos del proveedor por su ID
        viewModel.obtenerDatosProveedorPorId(proveedorId)
    }
}
