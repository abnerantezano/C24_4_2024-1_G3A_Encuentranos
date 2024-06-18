package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.adapters.DetalleContratoAdapater
import com.ambrosio.josue.tutorial.databinding.ActivityMiContratoBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.viewModels.ContratoViewModel
import com.ambrosio.josue.tutorial.viewModels.InicioViewModel

class MiContratoActivity : HeaderInclude() {
    private lateinit var binding: ActivityMiContratoBinding
    private lateinit var contractsViewModel: ContratoViewModel
    private lateinit var adapter: DetalleContratoAdapater
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiContratoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contractsViewModel = ContratoViewModel(this)

        // Mostrar el ProgressBar y ocultar el contenido al iniciar la actividad
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewMensajes.visibility = View.GONE
        binding.textViewError.visibility = View.GONE

        contractsViewModel.obtenerDetalleContratos()

        adapter = DetalleContratoAdapater()

        viewModel.obtenerIdProveedor()

        observeViewModel()
        setupHeader()
    }

    private fun observeViewModel() {
        viewModel.idProveedor.observe(this, Observer { idProveedor ->
            if (idProveedor != null) {
                contractsViewModel.obtenerDetalleContratoPorIdProveedor(idProveedor)
            }
        })

        contractsViewModel.obtenerDetalleContratoPorIdProveedor.observe(this, Observer { detalleContrato ->
            binding.progressBar.visibility = View.GONE
            if (detalleContrato != null && detalleContrato.isNotEmpty()) {
                binding.recyclerViewMensajes.visibility = View.VISIBLE
                binding.textViewError.visibility = View.GONE
                adapter.submitList(detalleContrato)
                binding.recyclerViewMensajes.adapter = adapter
            } else {
                binding.recyclerViewMensajes.visibility = View.GONE
                binding.textViewError.visibility = View.VISIBLE
                binding.textViewError.text = "No se encontraron contratos."
            }
        })

        viewModel.mensajeError.observe(this, Observer { errorMessage ->
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewMensajes.visibility = View.GONE
            binding.textViewError.visibility = View.VISIBLE
            errorMessage?.let {
                binding.textViewError.text = it
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            } ?: run {
                binding.textViewError.text = "No hubo conexión."
            }
        })
    }
}
