package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.activity.viewModels
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.adapters.MiServicioAdapater
import com.ambrosio.josue.tutorial.databinding.ActivityMiServicioBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.models.ServicioProveedorModel
import com.ambrosio.josue.tutorial.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.viewModels.ServicioProveedorViewModel
import com.ambrosio.josue.tutorial.viewModels.ServiciosListViewModel

class MiServicioActivity : HeaderInclude() {

    private lateinit var binding: ActivityMiServicioBinding
    private lateinit var miServicio: ServicioProveedorViewModel
    private lateinit var serviciosListViewModel: ServiciosListViewModel

    private lateinit var adapter: MiServicioAdapater
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiServicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviciosListViewModel = ViewModelProvider(this).get(ServiciosListViewModel::class.java)
        serviciosListViewModel.obtenerServicios()
        miServicio = ServicioProveedorViewModel()
        adapter = MiServicioAdapater(this)

        // Mostrar el ProgressBar y ocultar el contenido al iniciar la actividad
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewServicios.visibility = View.GONE
        observeViewModel()

        viewModel.obtenerIdProveedor()

        // Configurar el click listener para el botón de agregar servicio
        binding.tvAgregarServicio.setOnClickListener {
            mostrarDialogoAgregarServicio()
        }

        setupHeader()
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

    private fun mostrarDialogoAgregarServicio() {
        viewModel.idProveedor.observe(this, Observer { idProveedor ->
            if (idProveedor != null) {
                miServicio.listarServiciosNoRegistrados(idProveedor)
            }
        })
        // Inflar el layout del diálogo
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_editar_servicio, null)

        // Crear el diálogo
        val dialog = AlertDialog.Builder(this)
            .setTitle("Agregar Servicio")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, _ ->
                // Aquí puedes agregar la lógica para guardar el servicio
                // Por ejemplo, obtener los datos ingresados en el diálogo
                // y realizar alguna acción
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()

        // Configurar el Spinner en el diálogo después de que el diálogo haya sido mostrado
        val spinnerServicios = dialogView.findViewById<Spinner>(R.id.spinnerServicios)
        miServicio.listarServiciosNoRegistrados.observe(this, Observer { servicios ->
            if (servicios != null && servicios.isNotEmpty()) {
                val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, servicios.map { it.nombre })
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerServicios.adapter = spinnerAdapter
            } else {
                Toast.makeText(this, "Error al obtener la lista de servicios", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun mostrarDialogoEditarServicio(servicio: ServicioProveedorModel) {
        // Inflar el layout del diálogo
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_editar_servicio, null)

        // Crear el diálogo
        val dialog = AlertDialog.Builder(this)
            .setTitle("Editar Servicio")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, _ ->
                // Aquí puedes agregar la lógica para guardar el servicio
                // Por ejemplo, obtener los datos ingresados en el diálogo
                // y realizar alguna acción
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        // Cargar los datos del servicio en los campos del diálogo
        val edtPrecioServicio = dialogView.findViewById<EditText>(R.id.edtPrecioServicio)
        edtPrecioServicio.setText(servicio.precio.toString())

        dialog.show()

        // Configurar el Spinner en el diálogo después de que el diálogo haya sido mostrado
        val spinnerServicios = dialogView.findViewById<Spinner>(R.id.spinnerServicios)
        serviciosListViewModel.listaServicios.observe(this, Observer { servicios ->
            if (servicios != null && servicios.isNotEmpty()) {
                val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, servicios.map { it.nombre })
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerServicios.adapter = spinnerAdapter
                val position = servicios.indexOfFirst { it.nombre == servicio.idServicio.nombre }
                if (position >= 0) {
                    spinnerServicios.setSelection(position)
                }
            } else {
                Toast.makeText(this, "Error al obtener la lista de servicios", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
