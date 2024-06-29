package com.ambrosio.josue.tutorial.ui.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.MainActivity
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.ActivityRegistroProveedorBinding
import com.ambrosio.josue.tutorial.data.models.ClienteModel
import com.ambrosio.josue.tutorial.data.models.DistritoModel
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.data.models.TipoUsuarioModel
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.ui.viewModels.DistritoViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.RegistroProveedorOClienteViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RegistroProveedorOClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroProveedorBinding
    private lateinit var viewModel: RegistroProveedorOClienteViewModel
    private lateinit var distritoViewModel: DistritoViewModel
    private var tipoId: Int = -1
    private var userId: Int = -1
    private var email: String = ""
    private lateinit var sharedPreferences: SharedPreferences
    val seleccionarCalendario = Calendar.getInstance()

    companion object {
        private const val TAG = "RegistroProveedorActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroProveedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        inicializarViewModels()
        recuperarDatosSharedPreferences()
        configurarObservadores()
        configurarListeners()

        binding.edtFechaNacimiento.setOnClickListener{
            crearFechaNacimiento()
        }
        distritoViewModel.listarDistritos()
    }

    private fun inicializarViewModels() {
        val proveedorApi = RetrofitClient.proveedorApi
        val distritoApi = RetrofitClient.distritoApi
        val clienteApi = RetrofitClient.clienteApi
        viewModel = RegistroProveedorOClienteViewModel(proveedorApi, distritoApi, clienteApi)
        distritoViewModel = DistritoViewModel(distritoApi)
    }

    private fun recuperarDatosSharedPreferences() {
        userId = sharedPreferences.getInt("user_id", -1)
        email = sharedPreferences.getString("email", "") ?: ""
        tipoId = sharedPreferences.getInt("tipo_id", -1)
    }

    private fun configurarObservadores() {
        distritoViewModel.distritos.observe(this, Observer { distritos ->
            if (distritos.isNotEmpty()) {
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, distritos.map { it.nombre })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.distritoSpinner.adapter = adapter
            } else {
                Toast.makeText(this, "Error al obtener la lista de distritos", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.registroResult.observe(this, Observer { exitoso ->
            if (exitoso) {

                val idRegistro = viewModel.idRegistro.value ?: -1
                sharedPreferences.edit().putInt("id_proveedor", idRegistro).apply()

                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                navegarSegunTipoUsuario()
            } else {
                Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun configurarListeners() {
        binding.btnEnviar.setOnClickListener { registrar() }
    }

    private fun registrar() {
        val nombre = binding.editTextNombreCompleto.text.toString()
        val apellidoPaterno = binding.editTextApellidoPaterno.text.toString()
        val apellidoMaterno = binding.editTextApellidoMaterno.text.toString()
        val fechaNacimientoStr = binding.edtFechaNacimiento.text.toString()
        val sexo = when (binding.radioSexo.checkedRadioButtonId) {
            R.id.radio_masculino -> "M"
            R.id.radio_femenino -> "F"
            else -> ""
        }
        val dni = binding.editTextDni.text.toString()
        val celular = binding.editTextCelular.text.toString()
        val idDistrito = binding.distritoSpinner.selectedItemPosition + 1

        val fechaNacimiento: Date? = try {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaNacimientoStr)
        } catch (e: Exception) {
            null
        }

        if (fechaNacimiento == null) {
            Toast.makeText(this, "Introduce una fecha válida en formato dd-MM-yyyy", Toast.LENGTH_SHORT).show()
            return
        }

        val formattedFechaNacimiento = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fechaNacimiento)

        val tipoUsuario = TipoUsuarioModel(tipoId, if (tipoId == 1) "Proveedor" else "Cliente")
        val usuario = UsuarioModel(userId, tipoUsuario)
        val distrito = DistritoModel(idDistrito, "")

        val nuevoRegistro = if (tipoId == 1) {
            // Registro para proveedor
            ClienteModel(
                idCliente = 0, // Suponiendo que el id se genera automáticamente en el backend
                idUsuario = usuario,
                idDistrito = distrito,
                nombre = nombre,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                sexo = sexo,
                dni = dni,
                celular = celular,
                fechaRegistro = "2000-12-12", // Puedes dejarlo como está o configurarlo según backend
                fechaNacimiento = formattedFechaNacimiento
            )
        } else {
            ProveedorModel(
                idProveedor = 0, // Suponiendo que el id se genera automáticamente en el backend
                idUsuario = usuario,
                idDistrito = distrito,
                nombre = nombre,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                sexo = sexo,
                dni = dni,
                celular = celular,
                fechaRegistro = "2000-12-12", // Puedes dejarlo como está o configurarlo según backend
                fechaNacimiento = formattedFechaNacimiento
            )
        }

        // Log de datos antes de enviar la solicitud
        Log.d(TAG, "Datos a enviar: $nuevoRegistro")

        viewModel.registrar(nuevoRegistro)
    }

    private fun crearFechaNacimiento() {
        val edtCalendario = binding.edtFechaNacimiento
        val year = seleccionarCalendario.get(Calendar.YEAR)
        val mes = seleccionarCalendario.get(Calendar.MONTH)
        val dia = seleccionarCalendario.get(Calendar.DAY_OF_MONTH)
        val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            seleccionarCalendario.set(y, m, d)
            edtCalendario.setText("$y-$m-$d")
        }

        val datePickerDialog = DatePickerDialog(this, listener, year, mes, dia)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -18)
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        datePickerDialog.show()
    }



    private fun navegarSegunTipoUsuario() {
        val intent = if (tipoId == 1) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, AgregarServicioActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
