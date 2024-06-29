package com.ambrosio.josue.tutorial.ui.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.ClienteModel
import com.ambrosio.josue.tutorial.data.models.ContratoModel
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModeloId
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.data.models.ServicioModel
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel
import com.ambrosio.josue.tutorial.databinding.ActivityCrearContratoBinding
import com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment.MiContratoActivity
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
    val seleccionarCalendario = Calendar.getInstance()


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

        binding.edtFechaInicio.setOnClickListener{ crearFechaInicioOFin(binding.edtFechaInicio) }
        binding.edtFechaFin.setOnClickListener{ crearFechaInicioOFin(binding.edtFechaFin) }

        binding.edtHoraInicio.setOnClickListener { horaContratoDialog(binding.edtHoraInicio) }
        binding.edtHoraFin.setOnClickListener { horaContratoDialog(binding.edtHoraFin) }

    }

    private fun crearContrato() {
        if (!validarCampos()) {
            Toast.makeText(this, "Faltan campos por completar", Toast.LENGTH_SHORT).show()
            return
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()

        val fechaInicioCalendar = Calendar.getInstance().apply {
            time = sdf.parse(binding.edtFechaInicio.text.toString())
            add(Calendar.DAY_OF_MONTH, 1) // Ajustar un día adelante
        }
        val fechaFinCalendar = Calendar.getInstance().apply {
            time = sdf.parse(binding.edtFechaFin.text.toString())
            add(Calendar.DAY_OF_MONTH, 1)
        }

        val fechaInicioFormatted = sdf.format(fechaInicioCalendar.time)
        val fechaFinFormatted = sdf.format(fechaFinCalendar.time)
        val estado = "Pendiente"
        val precioFinal = binding.edtPrecioServicio.text.toString().toDouble()

        val hiServicio = convertirHoraUTC(binding.edtHoraInicio.text.toString())
        val hfServicio = convertirHoraUTC(binding.edtHoraFin.text.toString())

        val fhCreacion = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.format(Date())

        var idCliente: Int? = null

        inicioViewModel.idCliente.observe(this, Observer { id ->
            idCliente = id
            if (idCliente != null) {
                val cliente = ClienteModel(idCliente!!)
                val contrato = ContratoModel(
                    idContrato = 0,
                    idCliente = cliente,
                    fechaInicio = fechaInicioFormatted,
                    fechaFin = fechaFinFormatted,
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
                                mostrarDialogoInformacionAgregada(newContrato.idContrato)
                            } else {
                                Toast.makeText(this, "Error al crear el detalle del contrato", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Error al crear el contrato", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun validarCampos(): Boolean {
        return binding.edtFechaInicio.text.isNotEmpty() &&
                binding.edtFechaFin.text.isNotEmpty() &&
                binding.edtHoraInicio.text.isNotEmpty() &&
                binding.edtHoraFin.text.isNotEmpty() &&
                binding.edtPrecioServicio.text.isNotEmpty() &&
                binding.spnServicio.selectedItem != null
    }

    private fun convertirHoraUTC(horaLocal: String): String {
        val sdfLocal = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val dateLocal = sdfLocal.parse(horaLocal)

        val sdfUTC = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        return sdfUTC.format(dateLocal)
    }

    private fun setupObservers() {
        servicioProveedorViewModel.obtenerServicioProveedorPorIdProveedor.observe(this, Observer { servicios ->
            if (servicios != null) {
                spinnerAdapter.clear()
                spinnerAdapter.addAll(servicios)
                spinnerAdapter.notifyDataSetChanged()
            }
        })
        binding.spnServicio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val servicioSeleccionado = parent.getItemAtPosition(position) as ServicioProveedorModel
                if (!servicioSeleccionado.negociable) {
                    binding.edtPrecioServicio.setText(servicioSeleccionado.precio.toString())
                    binding.edtPrecioServicio.isEnabled = false
                } else {
                    binding.edtPrecioServicio.text.clear()
                    binding.edtPrecioServicio.isEnabled = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun crearFechaInicioOFin(editText: EditText) {
        val year = seleccionarCalendario.get(Calendar.YEAR)
        val mes = seleccionarCalendario.get(Calendar.MONTH)
        val dia = seleccionarCalendario.get(Calendar.DAY_OF_MONTH)

        val calendarActual = Calendar.getInstance()
        calendarActual.add(Calendar.DAY_OF_MONTH, 0)

        val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            seleccionarCalendario.set(y, m, d)
            editText.setText("$y-${m + 1}-$d")
        }

        val datePickerDialog = DatePickerDialog(this, listener, year, mes, dia)

        datePickerDialog.datePicker.minDate = calendarActual.timeInMillis

        datePickerDialog.show()
    }


    private fun horaContratoDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            editText.setText(String.format("%02d:%02d:00", selectedHour, selectedMinute))
        }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun mostrarDialogoInformacionAgregada(idContrato: Int) {
        val dialogView = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_cambios_exito, null)
        val btnExito = dialogView.findViewById<Button>(R.id.btnExito)
        val tvCambios = dialogView.findViewById<TextView>(R.id.tvCambios)
        tvCambios.text = "Se creó el contrato correctamente. Espera la respuesta del proveedor."

        val dialog = android.app.AlertDialog.Builder(this, R.style.AlertDialogCustom)
            .setView(dialogView)
            .create()

        btnExito.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, MiContratoActivity::class.java)
            intent.putExtra("CONTRATO_ID", idContrato)
            startActivity(intent)
            finish()
        }

        dialog.show()
    }

}
