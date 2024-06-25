package com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.activity.viewModels
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.ui.adapters.MiServicioAdapater
import com.ambrosio.josue.tutorial.databinding.ActivityMiServicioBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModeloId
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.ServicioProveedorViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.ServiciosListViewModel

class MiServicioActivity : HeaderInclude() {

    private lateinit var binding: ActivityMiServicioBinding
    private lateinit var miServicio: ServicioProveedorViewModel
    private lateinit var serviciosListViewModel: ServiciosListViewModel
    private var idProveedor: Int = -1

    private lateinit var adapter: MiServicioAdapater
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiServicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviciosListViewModel = ViewModelProvider(this).get(ServiciosListViewModel::class.java)
        serviciosListViewModel.obtenerServicios()

        miServicio =
            ViewModelProvider(this).get(ServicioProveedorViewModel::class.java) // Inicializar miServicio correctamente
        adapter = MiServicioAdapater(this)

        // Mostrar el ProgressBar y ocultar el contenido al iniciar la actividad
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewServicios.visibility = View.GONE
        observeViewModel()

        viewModel.obtenerIdProveedor()

        binding.tvAgregarServicio.setOnClickListener {
            mostrarDialogoAgregarServicio()
        }

        setupHeader()
    }

    private fun observeViewModel() {
        viewModel.idProveedor.observe(this, Observer { idProveedor ->
            if (idProveedor != null) {
                this.idProveedor = idProveedor
                miServicio.obtenerServicioProveedorPorIdProveedor(idProveedor)
            }
        })

        miServicio.obtenerServicioProveedorPorIdProveedor.observe(this, Observer { servicio ->
            binding.progressBar.visibility = View.GONE
            if (servicio != null) {
                binding.recyclerViewServicios.visibility = View.VISIBLE
                adapter.submitList(servicio)
                binding.recyclerViewServicios.adapter = adapter

                // Verificar si ya existen 5 servicios registrados para ocultar el botón
                if (servicio.size >= 5) {
                    binding.tvAgregarServicio.visibility = View.GONE
                } else {
                    binding.tvAgregarServicio.visibility = View.VISIBLE
                }
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
        val dialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_agregar_editar_servicio, null)

        // Crear el diálogo
        val dialog = AlertDialog.Builder(this)
            .setTitle("Agregar Servicio")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, _ ->
                val spinnerServicios = dialogView.findViewById<Spinner>(R.id.spinnerServicios)
                val edtPrecioServicio = dialogView.findViewById<EditText>(R.id.edtPrecioServicio)

                // Obtener el servicio seleccionado
                val servicioSeleccionado =
                    spinnerServicios.selectedItem as String  // Obtener el nombre del servicio

                val precio = edtPrecioServicio.text.toString().toDoubleOrNull()

                if (precio != null) {
                    // Encontrar el objeto ServicioModel correspondiente al nombre seleccionado
                    val servicioModelSeleccionado =
                        serviciosListViewModel.listaServicios.value?.find { it.nombre == servicioSeleccionado }

                    if (servicioModelSeleccionado != null) {
                        // Crear el objeto ServicioProveedorModel para agregar
                        val servicioProveedor = ServicioProveedorModel(
                            id = ServicioProveedorModeloId(
                                servicioModelSeleccionado.idServicio,
                                idProveedor
                            ),
                            idServicio = servicioModelSeleccionado,
                            idProveedor = ProveedorModel(idProveedor = idProveedor),
                            precio = precio,
                            negociable = true
                        )

                        // Llamar al método para agregar el servicio proveedor
                        miServicio.agregarServicioProveedor(listOf(servicioProveedor)) { success ->
                            if (success) {
                                Toast.makeText(
                                    this,
                                    "Servicio agregado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Actualizar la lista de servicios después de agregar uno nuevo
                                miServicio.obtenerServicioProveedorPorIdProveedor(idProveedor) // Esto debería actualizar la lista automáticamente
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error al agregar servicio",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Error: no se encontró el servicio seleccionado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this, "Ingrese un precio válido", Toast.LENGTH_SHORT).show()
                }
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
                val spinnerAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    servicios.map { it.nombre })
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerServicios.adapter = spinnerAdapter
            } else {
                Toast.makeText(this, "Error al obtener la lista de servicios", Toast.LENGTH_SHORT)
                    .show()
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
                val spinnerServicios = dialogView.findViewById<Spinner>(R.id.spinnerServicios)
                val edtPrecioServicio = dialogView.findViewById<EditText>(R.id.edtPrecioServicio)

                // Obtener el servicio seleccionado
                val servicioSeleccionado = spinnerServicios.selectedItem as String  // Obtener el nombre del servicio

                val precio = edtPrecioServicio.text.toString().toDoubleOrNull()

                if (precio != null) {
                    // Encontrar el objeto ServicioModel correspondiente al nombre seleccionado
                    val servicioModelSeleccionado = serviciosListViewModel.listaServicios.value?.find { it.nombre == servicioSeleccionado }

                    if (servicioModelSeleccionado != null) {
                        // Actualizar los campos del servicioProveedor
                        servicio.id.idServicio = servicioModelSeleccionado.idServicio
                        servicio.idServicio = servicioModelSeleccionado
                        servicio.precio = precio

                        // Llamar al método para actualizar el servicio proveedor
                        miServicio.actualizarServicioProveedor(servicio) { success ->
                            if (success) {
                                Toast.makeText(this, "Servicio actualizado correctamente", Toast.LENGTH_SHORT).show()

                                // Actualizar la lista de servicios después de actualizar uno existente
                                miServicio.obtenerServicioProveedorPorIdProveedor(idProveedor)
                            } else {
                                Toast.makeText(this, "Error al actualizar servicio", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Error: no se encontró el servicio seleccionado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Ingrese un precio válido", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Eliminar") { dialog, _ ->
                // Mostrar un diálogo de confirmación antes de eliminar el servicio
                AlertDialog.Builder(this)
                    .setTitle("Confirmar Eliminación")
                    .setMessage("¿Está seguro de que desea eliminar este servicio?")
                    .setPositiveButton("Eliminar") { _, _ ->
                        // Llamar al método para eliminar el servicio proveedor
                        miServicio.eliminarServicioProveedor(servicio.id) { success ->
                            if (success) {
                                Toast.makeText(this, "Servicio eliminado correctamente", Toast.LENGTH_SHORT).show()

                                // Actualizar la lista de servicios después de eliminar uno existente
                                miServicio.obtenerServicioProveedorPorIdProveedor(idProveedor)
                            } else {
                                Toast.makeText(this, "Error al eliminar servicio", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .setNegativeButton("Cancelar") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
            .create()

        // Mostrar el precio actual del servicio en el EditText
        val edtPrecioServicio = dialogView.findViewById<EditText>(R.id.edtPrecioServicio)
        edtPrecioServicio.setText(servicio.precio.toString())

        // Mostrar el nombre del servicio actual seleccionado en el Spinner
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

        dialog.show()
    }
}