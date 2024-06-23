package com.ambrosio.josue.tutorial.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel
import com.ambrosio.josue.tutorial.databinding.ActivityCrearContratoBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.ui.adapters.ServicioSpinnerAdapter
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.ServicioProveedorViewModel

class CrearContratoActivity : HeaderInclude() {

    private lateinit var binding: ActivityCrearContratoBinding
    private val servicioProveedorViewModel: ServicioProveedorViewModel by viewModels()
    private lateinit var spinnerAdapter: ServicioSpinnerAdapter
    private var serviciosList: MutableList<ServicioProveedorModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearContratoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerAdapter = ServicioSpinnerAdapter(this, serviciosList)
        binding.spnServicio.adapter = spinnerAdapter

        val proveedorId = intent.getIntExtra("PROVEEDOR_ID", -1)

        setupObservers()
        setupHeader()
        servicioProveedorViewModel.obtenerServicioProveedorPorIdProveedor(proveedorId)


    }

    private fun setupObservers() {
        servicioProveedorViewModel.obtenerServicioProveedorPorIdProveedor.observe(this, Observer { servicios ->
            if (servicios != null) {
                spinnerAdapter.clear()
                spinnerAdapter.addAll(servicios)
                spinnerAdapter.notifyDataSetChanged()
            }
        })
    }
}
