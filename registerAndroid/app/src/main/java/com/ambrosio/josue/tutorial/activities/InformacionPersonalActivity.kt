package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.databinding.ActivityPersonaInformationBinding
import com.ambrosio.josue.tutorial.viewModels.InicioViewModel
import java.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.android.gms.tasks.OnCompleteListener
import android.util.Log
import android.view.View

class InformacionPersonalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonaInformationBinding
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonaInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE
        binding.LinearDescription.visibility = View.GONE
        binding.btnActualizar.visibility = View.GONE

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
    }

    private fun updateSexoRadioButton(sexo: String) {
        when (sexo.toLowerCase(Locale.getDefault())) {
            "masculino" -> binding.radioMasculino.isChecked = true
            "femenino" -> binding.radioFemenino.isChecked = true
            else -> Log.e("InformacionPersonal", "Sexo desconocido: $sexo")
        }
    }
}
