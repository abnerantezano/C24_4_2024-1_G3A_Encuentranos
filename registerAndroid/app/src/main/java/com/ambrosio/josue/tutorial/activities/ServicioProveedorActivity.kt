package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambrosio.josue.tutorial.adapters.ServicioProveedorAdapter
import com.ambrosio.josue.tutorial.databinding.ActivityServicioProveedorBinding
import com.ambrosio.josue.tutorial.generals.FooterInclude
import com.ambrosio.josue.tutorial.viewModels.ServicioProveedorViewModel

class ServicioProveedorActivity : FooterInclude() {
    private lateinit var binding: ActivityServicioProveedorBinding
    private val servicioProveedorViewModel: ServicioProveedorViewModel by viewModels()
    private lateinit var adapter: ServicioProveedorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicioProveedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView y Adapter
        binding.recyclerViewProveedores.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProveedores.adapter = adapter

        // Observar cambios en la lista de servicios proveedores
        observeValues()

        // Iniciar la carga de servicios proveedores
        servicioProveedorViewModel.obtenerServiciosProveedor()

        // Configurar el footer
        setupFooter()
    }

    private fun observeValues() {
        servicioProveedorViewModel.listaServiciosProveedores.observe(this, Observer { servicios ->
            servicios?.let {
                adapter.submitList(it)
            }
        })
    }
}
