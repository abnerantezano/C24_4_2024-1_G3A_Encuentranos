package com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.databinding.ActivityMiContratoBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.ui.adapters.DetalleContratoAdapater
import com.ambrosio.josue.tutorial.ui.viewModels.ContratoViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel

class MiContratoActivity : HeaderInclude() {
    private lateinit var binding: ActivityMiContratoBinding
    private lateinit var contractsViewModel: ContratoViewModel
    private lateinit var adapter: DetalleContratoAdapater
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiContratoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contractsViewModel = ContratoViewModel()

        // Mostrar el ProgressBar y ocultar el contenido al iniciar la actividad
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewMensajes.visibility = View.GONE
        binding.textViewError.visibility = View.GONE

        contractsViewModel.obtenerDetalleContratos()

        adapter = DetalleContratoAdapater()

        viewModel.obtenerIdProveedor()

        observeViewModel()
        setupHeader()
        setupSearch()
    }

    private fun observeViewModel() {
        viewModel.idProveedor.observe(this, Observer { idProveedor ->
            if (idProveedor != null) {
                contractsViewModel.obtenerDetalleContratoPorProveedorYEstadoAceptado(idProveedor)
            }
        })

        contractsViewModel.detalleContratoPorIdProveedor.observe(this, Observer { detalleContrato ->
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

    private fun setupSearch() {
        binding.edtBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterContracts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterContracts(query: String) {
        val filteredList = contractsViewModel.detalleContratoPorIdProveedor.value?.filter {
            it.idContrato.idCliente.nombre.contains(query, ignoreCase = true)
        }
        if (filteredList != null) {
            adapter.submitList(filteredList)
        }
    }
}
