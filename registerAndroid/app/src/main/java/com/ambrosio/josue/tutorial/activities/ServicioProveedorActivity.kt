package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.ServicioProveedorAdapter
import com.ambrosio.josue.tutorial.databinding.ActivityServicioProveedorBinding
import com.ambrosio.josue.tutorial.generals.FooterInclude
import com.ambrosio.josue.tutorial.viewModels.ServicioProveedorViewModel

class ServicioProveedorActivity : FooterInclude() {
    private lateinit var binding: ActivityServicioProveedorBinding
    private lateinit var servicioProveedorViewModel: ServicioProveedorViewModel
    private lateinit var adapter: ServicioProveedorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicioProveedorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        servicioProveedorViewModel = ServicioProveedorViewModel(this)

        servicioProveedorViewModel.obtenerServiciosProveedor()

        adapter = ServicioProveedorAdapter()

        observeValues()
        setupFooter()
    }

    private fun observeValues() {
        servicioProveedorViewModel.listaServiciosProveedores.observe(this, Observer { servicios ->
            adapter.submitList(servicios)
            binding.recyclerViewProveedores.adapter = adapter
        })
    }
}