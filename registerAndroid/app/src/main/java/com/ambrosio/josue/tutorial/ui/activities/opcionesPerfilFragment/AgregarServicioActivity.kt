package com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.MainActivity
import com.ambrosio.josue.tutorial.ui.adapters.ServicioAdapter
import com.ambrosio.josue.tutorial.databinding.ActivityAgregarServicioBinding
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModeloId
import com.ambrosio.josue.tutorial.ui.viewModels.ServicioProveedorViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.ServiciosListViewModel

class AgregarServicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarServicioBinding
    private lateinit var serviciosListViewModel: ServiciosListViewModel
    private lateinit var adapter: ServicioAdapter
    private val servicioProveedorViewModel: ServicioProveedorViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private var idProveedor: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarServicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener id_proveedor desde SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        idProveedor = sharedPreferences.getInt("id_proveedor", -1)

        // Verificar si se está recuperando correctamente
        Log.d("AgregarServicioActivity", "ID Proveedor recuperado: $idProveedor")

        serviciosListViewModel = ServiciosListViewModel()
        serviciosListViewModel.obtenerServicios()
        adapter = ServicioAdapter()

        setupViews()
        observeValues()
    }



    private fun setupViews() {
        binding.apply {
            btnEnviar.setOnClickListener { agregarServicioProveedor() }
        }
    }

    private fun observeValues() {
        serviciosListViewModel.listaServicios.observe(this, Observer { servicios ->
            if (servicios != null && servicios.isNotEmpty()) {
                val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, servicios.map { it.nombre })
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.servicioSpinner.adapter = spinnerAdapter
            } else {
                Toast.makeText(this, "Error al obtener la lista de servicios", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun agregarServicioProveedor() {
        // Verificar que id_proveedor tenga un valor válido
        if (idProveedor == -1) {
            Toast.makeText(this, "Error: ID de proveedor no válido.", Toast.LENGTH_SHORT).show()
            return
        }

        // Resto del código para agregar el servicio usando idProveedor
        val selectedServicePosition = binding.servicioSpinner.selectedItemPosition
        val servicios = serviciosListViewModel.listaServicios.value
        val selectedService = servicios?.getOrNull(selectedServicePosition)

        if (selectedService == null) {
            Toast.makeText(this, "Seleccione un servicio válido.", Toast.LENGTH_SHORT).show()
            return
        }

        val precio = binding.edtPrecio.text.toString().toDoubleOrNull()
        if (precio == null || precio < 0) {
            Toast.makeText(this, "Ingrese un precio válido.", Toast.LENGTH_SHORT).show()
            return
        }

        val servicioProveedor = ServicioProveedorModel(
            id = ServicioProveedorModeloId(selectedService.idServicio, idProveedor),
            idServicio = selectedService,
            idProveedor = ProveedorModel(idProveedor = idProveedor),
            precio = precio,
            negociable = true
        )

        servicioProveedorViewModel.agregarServicioProveedor(listOf(servicioProveedor)) { success ->
            if (success) {
                Toast.makeText(this, "Servicio agregado exitosamente.", Toast.LENGTH_SHORT).show()
                navigateToHome()
            } else {
                Toast.makeText(this, "Error al agregar servicio.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}
