package com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.ActivityPerfilBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.PerfilViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult

class PerfilActivity : HeaderInclude() {

    private lateinit var binding: ActivityPerfilBinding
    private val perfilViewModel: PerfilViewModel by viewModels()
    private val inicioViewModel: InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mostrar el ProgressBar y ocultar el contenido al iniciar la actividad
        binding.progressBar.visibility = View.VISIBLE
        binding.imgFoto.visibility = View.GONE
        binding.linearDescripcion.visibility = View.GONE
        binding.linearCurriculum.visibility = View.GONE
        binding.btnGuardar.visibility = View.GONE

        val user = FirebaseAuth.getInstance().currentUser
        user?.getIdToken(true)
            ?.addOnCompleteListener(OnCompleteListener<GetTokenResult> { task ->
                if (task.isSuccessful) {
                    val idToken = task.result?.token
                    val email = user.email
                    if (email != null && idToken != null) {
                        inicioViewModel.obtenerNombreUsuario(email, idToken)
                    }
                } else {
                    Log.e("InformacionPersonal", "Error getting token: ${task.exception}")
                }
            })

        setupObservers()
        setupHeader()

        binding.btnGuardar.setOnClickListener {
            val descripcion = binding.tvDescripcionUsuario.text.toString()
            when (inicioViewModel.idTipo.value) {
                1 -> {
                    inicioViewModel.obtenerIdCliente()
                }
                2 -> {
                    inicioViewModel.obtenerIdProveedor()
                }
            }
        }

        inicioViewModel.idCliente.observe(this, Observer { idCliente ->
            val descripcion = binding.tvDescripcionUsuario.text.toString()
            perfilViewModel.actualizarDescripcionCliente(idCliente, descripcion)
        })

        inicioViewModel.idProveedor.observe(this, Observer { idProveedor ->
            val descripcion = binding.tvDescripcionUsuario.text.toString()
            perfilViewModel.actualizarDescripcionProveedor(idProveedor, descripcion)
        })

        perfilViewModel.descripcionActualizada.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Descripción actualizada con éxito", Toast.LENGTH_SHORT).show()
                mostrarDialogoInformacionActualizada()
            } else {
                Toast.makeText(this, "Error al actualizar la descripción", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setupObservers() {
        inicioViewModel.descripcionUsuario.observe(this, Observer { descripcion ->
            descripcion?.let {
                if (it != "null") {
                    binding.tvDescripcionUsuario.setText(it)
                } else {
                    binding.tvDescripcionUsuario.setHint(R.string.escribe_tu_descripci_n)
                }
            } ?: run {
                binding.tvDescripcionUsuario.setHint(R.string.escribe_tu_descripci_n)
            }
        })

        inicioViewModel.idTipo.observe(this, Observer { idTipo ->
            when (idTipo) {
                1 -> {
                    binding.progressBar.visibility = View.GONE
                    binding.imgFoto.visibility = View.VISIBLE
                    binding.linearDescripcion.visibility = View.VISIBLE
                    binding.linearCurriculum.visibility = View.GONE
                    binding.btnGuardar.visibility = View.VISIBLE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.imgFoto.visibility = View.VISIBLE
                    binding.linearDescripcion.visibility = View.VISIBLE
                    binding.linearCurriculum.visibility = View.VISIBLE
                    binding.btnGuardar.visibility = View.VISIBLE
                }
            }
        })
    }
    private fun mostrarDialogoInformacionActualizada() {
        val dialogView = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_cambios_exito, null)
        val btnExito = dialogView.findViewById<Button>(R.id.btnExito)

        val dialog = AlertDialog.Builder(this, R.style.AlertDialogCustom)
            .setView(dialogView)
            .create()

        btnExito.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}
