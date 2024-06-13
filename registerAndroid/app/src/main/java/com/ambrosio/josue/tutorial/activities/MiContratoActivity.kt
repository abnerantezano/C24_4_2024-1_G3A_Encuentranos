package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.adapters.DetalleContratoAdapater
import com.ambrosio.josue.tutorial.databinding.ActivityMiContratoBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.viewModels.ContratoViewModel

class MiContratoActivity : HeaderInclude() {
    private lateinit var binding: ActivityMiContratoBinding
    private lateinit var contractsViewModel: ContratoViewModel
    private lateinit var adapter: DetalleContratoAdapater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiContratoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contractsViewModel = ContratoViewModel(this)

        contractsViewModel.obtenerDetalleContratos()

        adapter = DetalleContratoAdapater()

        observeValues()
        setupHeader()
    }

    private fun observeValues() {
        contractsViewModel.listaDetalleContratoModel.observe(this, Observer { contratos ->
            adapter.submitList(contratos)
            binding.recyclerViewMensajes.adapter = adapter
        })
    }
}