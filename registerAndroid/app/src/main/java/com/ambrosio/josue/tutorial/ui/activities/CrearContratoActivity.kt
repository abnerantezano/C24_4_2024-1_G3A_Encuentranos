package com.ambrosio.josue.tutorial.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.data.models.ClienteModel
import com.ambrosio.josue.tutorial.data.models.ContratoModel
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModeloId
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.data.models.ServicioModel
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel
import com.ambrosio.josue.tutorial.databinding.ActivityCrearContratoBinding
import com.ambrosio.josue.tutorial.ui.adapters.ServicioSpinnerAdapter
import com.ambrosio.josue.tutorial.ui.viewModels.CrearContratoViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.ServicioProveedorViewModel
import java.text.SimpleDateFormat
import java.util.*

class CrearContratoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearContratoBinding
    private val servicioProveedorViewModel: ServicioProveedorViewModel by viewModels()
    private val crearContratoViewModel: CrearContratoViewModel by viewModels()
    private val inicioViewModel: InicioViewModel by viewModels()
    private lateinit var spinnerAdapter: ServicioSpinnerAdapter
    private var serviciosList: MutableList<ServicioProveedorModel> = mutableListOf()
    private var proveedorId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearContratoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerAdapter = ServicioSpinnerAdapter(this, serviciosList)
        binding.spnServicio.adapter = spinnerAdapter

        proveedorId = intent.getIntExtra("PROVEEDOR_ID", -1)

        binding.btnEnviar.setOnClickListener {
            crearContrato()
        }

        inicioViewModel.obtenerIdCliente()

        setupObservers()
        servicioProveedorViewModel.obtenerServicioProveedorPorIdProveedor(proveedorId)
    }

    private fun crearContrato() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaInicio = sdf.format(sdf.parse(binding.edtFechaInicio.text.toString()))
        val fechaFin = sdf.format(sdf.parse(binding.edtFechaFin.text.toString()))
        val estado = "Pendiente"
        val precioFinal = binding.edtPrecioServicio.text.toString().toDouble()
        val hiServicio = binding.edtHoraInicio.text.toString()
        val hfServicio = binding.edtHoraFin.text.toString()
        val fhCreacion = "2024-01-01"
        var idCliente: Int? = null

        inicioViewModel.idCliente.observe(this, Observer { id ->
            idCliente = id
            if (idCliente != null) {
                val cliente = ClienteModel(idCliente!!)
                val contrato = ContratoModel(
                    idContrato = 0,
                    idCliente = cliente,
                    fechaInicio = fechaInicio,
                    fechaFin = fechaFin,
                    estado = estado,
                    precioFinal = precioFinal,
                    hiServicio = hiServicio,
                    hfServicio = hfServicio,
                    fhCreacion = fhCreacion
                )

                crearContratoViewModel.crearContrato(contrato) { newContrato ->
                    if (newContrato != null) {
                        val servicioId = (binding.spnServicio.selectedItem as ServicioProveedorModel).idServicio.idServicio
                        val detalleContrato = DetalleContratoModel(
                            id = DetalleContratoModeloId(proveedorId, servicioId, newContrato.idContrato),
                            idProveedor = ProveedorModel(proveedorId),
                            idServicio = ServicioModel(servicioId),
                            idContrato = newContrato,
                            precioActual = precioFinal
                        )

                        crearContratoViewModel.crearDetalleContrato(detalleContrato) { newDetalleContrato ->
                            if (newDetalleContrato != null) {
                                Toast.makeText(this, "Contrato y detalle del contrato creados exitosamente", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Error al crear el detalle del contrato", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Error al crear el contrato", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Error al obtener el ID del cliente", Toast.LENGTH_SHORT).show()
            }
        })
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
