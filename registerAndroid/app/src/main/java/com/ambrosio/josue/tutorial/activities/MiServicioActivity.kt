package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.databinding.ActivityMiServicioBinding
import com.ambrosio.josue.tutorial.viewModels.ServicioProveedorViewModel
import androidx.activity.viewModels
import com.ambrosio.josue.tutorial.adapters.MIServicioAdapater
import com.ambrosio.josue.tutorial.viewModels.InicioViewModel

class MiServicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMiServicioBinding
    private lateinit var miServicio: ServicioProveedorViewModel
    private lateinit var adapter: MIServicioAdapater
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiServicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        miServicio = ServicioProveedorViewModel()
        adapter = MIServicioAdapater()

        // Mostrar el ProgressBar y ocultar el contenido al iniciar la actividad
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewServicios.visibility = View.GONE
        observeViewModel()

        viewModel.obtenerIdProveedor()
    }

    private fun observeViewModel() {
        viewModel.idProveedor.observe(this, Observer { idProveedor ->
            if (idProveedor != null) {
                miServicio.obtenerServicioProveedorPorIdProveedor(idProveedor)
            }
        })

        miServicio.obtenerServicioProveedorPorIdProveedor.observe(this, Observer { servicio ->
            binding.progressBar.visibility = View.GONE
            if (servicio != null) {
                binding.recyclerViewServicios.visibility = View.VISIBLE
                adapter.submitList(servicio)
                binding.recyclerViewServicios.adapter = adapter
            }
        })

        viewModel.mensajeError.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("MiServicioActivity", "Error: $errorMessage")
            }
        })
    }
}
