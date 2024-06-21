package com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.databinding.ActivityPersonaInformationBinding
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.DistritoViewModel
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.android.gms.tasks.OnCompleteListener

class InformacionPersonalActivity : HeaderInclude() {

    private lateinit var binding: ActivityPersonaInformationBinding
    private val viewModel: InicioViewModel by viewModels()
    private lateinit var distritoViewModel: DistritoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonaInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE
        binding.LinearDescription.visibility = View.GONE
        binding.btnActualizar.visibility = View.GONE

        setupViewModel()
        setupObservers()

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
    }

    private fun setupViewModel() {
        val distritoApi = RetrofitClient.distritoApi
        distritoViewModel = DistritoViewModel( distritoApi)
        distritoViewModel.listarDistritos()
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

        // Observing distritos and setting up the spinner
        distritoViewModel.distritos.observe(this, Observer { distritos ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, distritos.map { it.nombre })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.distritoSpinner.adapter = adapter

            // Observing proveedor to select the corresponding distrito and sexo
            viewModel.proveedor.observe(this, Observer { proveedor ->
                val selectedDistrito = distritos.find { it.idDistrito == proveedor.idDistrito.idDistrito }
                val position = distritos.indexOf(selectedDistrito)
                binding.distritoSpinner.setSelection(position)

                updateSexoRadioButton(proveedor.sexo)
            })
        })
    }

    private fun updateSexoRadioButton(sexo: String) {
        if (sexo == "Masculino"){
            binding.radioMasculino.isChecked = true
        }
        binding.radioFemenino.isChecked = true
    }

}
