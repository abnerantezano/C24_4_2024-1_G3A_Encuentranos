package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.adapters.DetalleContratoAdapater
import com.ambrosio.josue.tutorial.databinding.ActivityMyContractsBinding
import com.ambrosio.josue.tutorial.generals.FooterInclude
import com.ambrosio.josue.tutorial.viewModels.ContratoViewModel

class MyContractsActivity : FooterInclude() {
    private lateinit var binding: ActivityMyContractsBinding
    private lateinit var contractsViewModel: ContratoViewModel
    private lateinit var adapter: DetalleContratoAdapater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyContractsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contractsViewModel = ContratoViewModel(this)

        contractsViewModel.obtenerDetalleContratos()

        adapter = DetalleContratoAdapater()

        observeValues()
        setupFooter()
    }

    private fun observeValues() {
        contractsViewModel.listaDetalleContratoModel.observe(this, Observer { contratos ->
            adapter.submitList(contratos)
            binding.recyclerViewMensajes.adapter = adapter
        })
    }
}