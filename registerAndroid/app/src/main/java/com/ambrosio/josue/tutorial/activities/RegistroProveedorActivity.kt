package com.ambrosio.josue.tutorial.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.ActivityRegistroProveedorBinding
import com.ambrosio.josue.tutorial.models.DistritoModel
import com.ambrosio.josue.tutorial.models.ProveedorModel
import com.ambrosio.josue.tutorial.models.TipoUsuarioModel
import com.ambrosio.josue.tutorial.models.UsuarioModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.viewModels.RegistroProveedorViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistroProveedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroProveedorBinding
    private lateinit var viewModel: RegistroProveedorViewModel
    private var userId: Int = -1
    private var email: String = ""
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroProveedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        initializeViewModel()
        retrieveSharedPreferencesData()
        setupObservers()
        setupListeners()

        // Obtener la lista de distritos
        viewModel.listarDistritos()
    }

    private fun initializeViewModel() {
        val proveedorApi = RetrofitClient.proveedorApi
        val distritoApi = RetrofitClient.distritoApi
        viewModel = RegistroProveedorViewModel(proveedorApi, distritoApi)
    }

    private fun retrieveSharedPreferencesData() {
        userId = sharedPreferences.getInt("user_id", -1)
        email = sharedPreferences.getString("email", "") ?: ""

        // Establecer el ID del usuario en el TextView
        binding.tvObtenerId.text = "ID: $userId"
    }

    private fun setupObservers() {
        viewModel.distritos.observe(this, Observer { distritos ->
            if (distritos.isNotEmpty()) {
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, distritos.map { it.nombre })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.distritoSpinner.adapter = adapter
            } else {
                Toast.makeText(this, "Error al obtener la lista de distritos", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.registroProveedorResult.observe(this, Observer { isSuccessful ->
            if (isSuccessful) {
                // Guardar el idProveedor en SharedPreferences
                val idProveedor = viewModel.idProveedor.value ?: -1
                sharedPreferences.edit().putInt("id_proveedor", idProveedor).apply()

                Toast.makeText(this, "Proveedor registrado exitosamente", Toast.LENGTH_SHORT).show()
                navigateToAgregarServicio()
            } else {
                Toast.makeText(this, "Error al registrar proveedor", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setupListeners() {
        binding.btnEnviar.setOnClickListener { handleRegisterProveedor() }
    }

    private fun handleRegisterProveedor() {
        val nombre = binding.editTextNombreCompleto.text.toString()
        val apellidoPaterno = binding.editTextApellidoPaterno.text.toString()
        val apellidoMaterno = binding.editTextApellidoMaterno.text.toString()
        val fechaNacimientoStr = binding.editTextFechaNacimiento.text.toString()
        val sexo = when (binding.radioSexo.checkedRadioButtonId) {
            R.id.radio_masculino -> "Masculino"
            R.id.radio_femenino -> "Femenino"
            else -> ""
        }
        val dni = binding.editTextDni.text.toString()
        val celular = binding.editTextCelular.text.toString()
        val idDistrito = binding.distritoSpinner.selectedItemPosition + 1

        // Verificar si la fecha de nacimiento es válida
        val fechaNacimiento: Date? = try {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaNacimientoStr)
        } catch (e: Exception) {
            null
        }

        if (fechaNacimiento == null) {
            Toast.makeText(this, "Por favor, introduce una fecha válida en el formato yyyy-MM-dd", Toast.LENGTH_SHORT).show()
            return
        }

        val tipoUsuario = TipoUsuarioModel(1, "Proveedor")
        val nuevoProveedor = ProveedorModel(
            idProveedor = 0,
            idUsuario = UsuarioModel(userId, tipoUsuario, email, "", null, true, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())),
            idDistrito = DistritoModel(idDistrito, ""),
            nombre = nombre,
            apellidoPaterno = apellidoPaterno,
            apellidoMaterno = apellidoMaterno,
            sexo = sexo,
            dni = dni,
            celular = celular,
            fechaNacimiento = fechaNacimientoStr,
            calificacionPromedio = 0.0,
            curriculumUrl = "",
            fechaRegistro = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) // Agrega la fecha de registro actual
        )

        // Registrar el proveedor
        viewModel.registrarProveedor(nuevoProveedor)
    }

    private fun navigateToAgregarServicio() {
        val intent = Intent(this, AgregarServicioActivity::class.java)
        startActivity(intent)
        finish() // Finalizar esta actividad para evitar regresar a ella con el botón de atrás
    }
}
