package com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.DistritoModel
import com.ambrosio.josue.tutorial.databinding.ActivityPersonaInformationBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.ui.viewModels.DistritoViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.PerfilViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.android.gms.tasks.OnCompleteListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InformacionPersonalActivity : HeaderInclude() {

    private lateinit var binding: ActivityPersonaInformationBinding
    private val viewModel: InicioViewModel by viewModels()
    private val perfilViewModel: PerfilViewModel by viewModels()
    private lateinit var distritoViewModel: DistritoViewModel
    val seleccionarCalendario = Calendar.getInstance()
    private lateinit var distritoAdapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonaInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE
        binding.LinearDescription.visibility = View.GONE
        binding.btnActualizar.visibility = View.GONE

        setupViewModel()
        setupObservers()
        setupViews()

        binding.edtFechaNacimiento.setOnClickListener{
            crearFechaNacimiento()
        }

        val user = FirebaseAuth.getInstance().currentUser
        user?.getIdToken(true)
            ?.addOnCompleteListener(OnCompleteListener<GetTokenResult> { task ->
                if (task.isSuccessful) {
                    val idToken = task.result?.token
                    val email = user.email
                    if (email != null && idToken != null) {
                        viewModel.obtenerNombreUsuario(email, idToken)
                    }
                } else {
                    // Handle error -> task.exception
                    Log.e("InformacionPersonal", "Error getting token: ${task.exception}")
                }
            })

        setupHeader()

        binding.btnActualizar.setOnClickListener {
            val nombre = binding.edtNombre.text.toString()
            val apellidoPaterno = binding.edtApellidoPaterno.text.toString()
            val apellidoMaterno = binding.edtApellidoMaterno.text.toString()
            val celular = binding.edtCelular.text.toString()
            val idDistrito = binding.distritoSpinner.selectedItem as String // Obtener el nombre del distrito seleccionado
            val sexo = if (binding.radioMasculino.isChecked) "M" else "F"

            when (viewModel.idTipo.value) {
                1 -> {
                    viewModel.obtenerIdCliente()
                }
                2 -> {
                    viewModel.obtenerIdProveedor()
                }
            }
        }

        viewModel.idCliente.observe(this, Observer { idCliente ->
            val nombre = binding.edtNombre.text.toString()
            val apellidoPaterno = binding.edtApellidoPaterno.text.toString()
            val apellidoMaterno = binding.edtApellidoMaterno.text.toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fechaNacimiento = Calendar.getInstance().apply {
                time = sdf.parse(binding.edtFechaNacimiento.text.toString())
                add(Calendar.DAY_OF_MONTH, 1)
            }
            val fechaNacimientoFormatted = sdf.format(fechaNacimiento.time)
            val celular = binding.edtCelular.text.toString()
            val idDistrito = binding.distritoSpinner.selectedItem as String
            val sexo = if (binding.radioMasculino.isChecked) "M" else "F"

            // Buscar el objeto DistritoModel correspondiente al nombre seleccionado
            val distritoSeleccionado = distritoViewModel.distritos.value?.find { it.nombre == idDistrito }
            distritoSeleccionado?.let {
                perfilViewModel.actualizarInformacionCliente(
                    idCliente,
                    nombre,
                    apellidoPaterno,
                    apellidoMaterno,
                    fechaNacimientoFormatted,
                    celular,
                    it,
                    sexo
                )
            }
        })

        viewModel.idProveedor.observe(this, Observer { idProveedor ->
            val nombre = binding.edtNombre.text.toString()
            val apellidoPaterno = binding.edtApellidoPaterno.text.toString()
            val apellidoMaterno = binding.edtApellidoMaterno.text.toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fechaNacimiento = Calendar.getInstance().apply {
                time = sdf.parse(binding.edtFechaNacimiento.text.toString())
                add(Calendar.DAY_OF_MONTH, 1)
            }
            val fechaNacimientoFormatted = sdf.format(fechaNacimiento.time)
            val celular = binding.edtCelular.text.toString()
            val idDistrito = binding.distritoSpinner.selectedItem as String
            val sexo = if (binding.radioMasculino.isChecked) "M" else "F"

            // Buscar el objeto DistritoModel correspondiente al nombre seleccionado
            val distritoSeleccionado = distritoViewModel.distritos.value?.find { it.nombre == idDistrito }
            distritoSeleccionado?.let {
                perfilViewModel.actualizarInformacionProveedor(
                    idProveedor,
                    nombre,
                    apellidoPaterno,
                    apellidoMaterno,
                    fechaNacimientoFormatted,
                    celular,
                    it,
                    sexo
                )
            }
        })

        perfilViewModel.informacionActualizada.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Información actualizada con éxito", Toast.LENGTH_SHORT).show()
                mostrarDialogoInformacionActualizada()
            } else {
                Toast.makeText(this, "Error al actualizar la información", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupViewModel() {
        val distritoApi = RetrofitClient.distritoApi
        distritoViewModel = DistritoViewModel(distritoApi)
        distritoViewModel.listarDistritos()
    }

    private fun setupViews() {
        // Configurar el adaptador del Spinner
        distritoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf())
        distritoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.distritoSpinner.adapter = distritoAdapter
    }

    private fun setupObservers() {
        viewModel.nombreUsuario.observe(this, Observer { nombre ->
            binding.progressBar.visibility = View.GONE
            binding.LinearDescription.visibility = View.VISIBLE
            binding.btnActualizar.visibility = View.VISIBLE
            binding.edtNombre.setText(nombre)
        })

        viewModel.apellidoPaternoUsuario.observe(this, Observer { apellido ->
            binding.edtApellidoPaterno.setText(apellido)
        })

        viewModel.apellidoMaternoUsuario.observe(this, Observer { apellido ->
            binding.edtApellidoMaterno.setText(apellido)
        })

        viewModel.fechaNacimiento.observe(this, Observer { fechaNacimiento ->
            binding.edtFechaNacimiento.setText(fechaNacimiento)
        })

        viewModel.dniUsuario.observe(this, Observer { dni ->
            binding.edtDni.setText(dni)
        })

        viewModel.celularUsuario.observe(this, Observer { celular ->
            binding.edtCelular.setText(celular)
        })

        viewModel.sexoUsuario.observe(this, Observer { sexo ->
            Log.d("InformacionPersonal", "Sexo observado: $sexo")
            when (sexo) {
                "M" -> {
                    binding.radioMasculino.isChecked = true
                    binding.radioFemenino.isChecked = false
                }
                "F" -> {
                    binding.radioMasculino.isChecked = false
                    binding.radioFemenino.isChecked = true
                }
                else -> {
                    // Por defecto, si el sexo no es "M" ni "F"
                    binding.radioMasculino.isChecked = true
                    binding.radioFemenino.isChecked = false
                }
            }
        })

        viewModel.distritoUsuario.observe(this, Observer { distrito ->
            distrito?.let {
                binding.distritoSpinner.setSelection(distritoAdapter.getPosition(it.nombre))
            }
        })
        distritoViewModel.distritos.observe(this, Observer { distritos ->
            val nombresDistritos = distritos.map { it.nombre }
            distritoAdapter.clear()
            distritoAdapter.addAll(nombresDistritos)

            viewModel.client.observe(this, Observer { usuario ->
                usuario?.let {
                    Log.d("InformacionPersonal", "Distrito usuario: ${usuario.idDistrito?.nombre}")
                    seleccionarDistrito(usuario.idDistrito?.idDistrito, distritos)
                }
            })

            viewModel.proveedor.observe(this, Observer { proveedor ->
                proveedor?.let {
                    Log.d("InformacionPersonal", "Distrito proveedor: ${proveedor.idDistrito?.nombre}")
                    seleccionarDistrito(proveedor.idDistrito?.idDistrito, distritos)
                }
            })
        })
    }

    private fun seleccionarDistrito(idDistrito: Int?, distritos: List<DistritoModel>) {
        idDistrito?.let { id ->
            val distritoSeleccionado = distritos.find { it.idDistrito == id }
            distritoSeleccionado?.let {
                val position = distritoAdapter.getPosition(it.nombre)
                if (position != -1) {
                    binding.distritoSpinner.setSelection(position)
                }
            }
        }
    }
    private fun mostrarDialogoInformacionActualizada() {
        val dialogView = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_cambios_exito, null)
        val btnExito = dialogView.findViewById<Button>(R.id.btnExito)
        val tvCambios = dialogView.findViewById<TextView>(R.id.tvCambios)

        val dialog = AlertDialog.Builder(this, R.style.AlertDialogCustom)
            .setView(dialogView)
            .create()

        btnExito.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun crearFechaNacimiento() {
        val edtCalendario = binding.edtFechaNacimiento
        val year = seleccionarCalendario.get(Calendar.YEAR)
        val mes = seleccionarCalendario.get(Calendar.MONTH)
        val dia = seleccionarCalendario.get(Calendar.DAY_OF_MONTH)
        val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            seleccionarCalendario.set(y, m, d)
            edtCalendario.setText("$y-${m + 1}-$d")
        }

        val datePickerDialog = DatePickerDialog(this, listener, year, mes, dia)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -18)
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        datePickerDialog.show()
    }

}
