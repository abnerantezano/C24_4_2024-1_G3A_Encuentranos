package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.adapters.ServicioAdapter
import com.ambrosio.josue.tutorial.databinding.ActivityServiciosListBinding
import com.ambrosio.josue.tutorial.generals.FooterInclude
import com.ambrosio.josue.tutorial.viewModels.ServiciosListViewModel

class ServiciosListActivity : FooterInclude() {
    private lateinit var binding: ActivityServiciosListBinding
    private lateinit var serviciosListViewModel: ServiciosListViewModel
    private lateinit var adapter: ServicioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiciosListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        serviciosListViewModel = ServiciosListViewModel()

        serviciosListViewModel.obtenerServicios()

        adapter = ServicioAdapter()

        observeValues()
        setupFooter()
    }

    private fun observeValues() {
        serviciosListViewModel.listaServicios.observe(this, Observer { servicios ->
            adapter.submitList(servicios)
            binding.recyclerViewServicios.adapter = adapter
        })
    }
}
