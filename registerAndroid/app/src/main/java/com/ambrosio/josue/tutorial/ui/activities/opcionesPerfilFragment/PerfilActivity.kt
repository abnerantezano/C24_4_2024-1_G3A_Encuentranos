package com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.ActivityPerfilBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.PerfilViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.squareup.picasso.Picasso

class PerfilActivity : HeaderInclude() {

    private lateinit var binding: ActivityPerfilBinding
    private val perfilViewModel: PerfilViewModel by viewModels()
    private val inicioViewModel: InicioViewModel by viewModels()
    private var selectedImageFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mostrar el ProgressBar y ocultar el contenido al iniciar la actividad
        binding.progressBar.visibility = View.VISIBLE
        binding.imgUsuario.visibility = View.GONE
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

        binding.imgUsuario.setOnClickListener {
            abrirGaleriaParaSeleccionarImagen()
        }

        binding.btnGuardar.setOnClickListener {
            val descripcion = binding.tvDescripcionUsuario.text.toString()
            if (selectedImageFileUri != null) {
                guardarImagen(selectedImageFileUri!!)
            }

            when (inicioViewModel.idTipo.value) {
                1 -> inicioViewModel.obtenerIdCliente()
                2 -> inicioViewModel.obtenerIdProveedor()
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

    private fun abrirGaleriaParaSeleccionarImagen() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "/"
            registerForFiles.launch(this)
        }
    }

    private val registerForFiles =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                selectedImageFileUri = result.data?.data
                binding.imgUsuario.setImageURI(selectedImageFileUri)
            }
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
        inicioViewModel.imagenUrl.observe(this, Observer { imagenUrl ->
            val imgUsuario = binding.imgUsuario
            Picasso.get().load(imagenUrl).into(imgUsuario)
        })

        inicioViewModel.idTipo.observe(this, Observer { idTipo ->
            when (idTipo) {
                1 -> {
                    binding.progressBar.visibility = View.GONE
                    binding.imgUsuario.visibility = View.VISIBLE
                    binding.linearDescripcion.visibility = View.VISIBLE
                    binding.linearCurriculum.visibility = View.GONE
                    binding.btnGuardar.visibility = View.VISIBLE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.imgUsuario.visibility = View.VISIBLE
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

    private fun guardarImagen(imageUri: Uri) {
        val contentResolver = applicationContext.contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)
        val fileSize = getFileSize(contentResolver, imageUri)
        val mimeType = contentResolver.getType(imageUri)

        if (inputStream != null && fileSize != null && mimeType != null) {
            val usuario = inicioViewModel.usuario.value
            if (usuario != null) {
                perfilViewModel.actualizarImagenUsuario(usuario, imageUri, fileSize, mimeType, inputStream)
            }
        }
    }

    private fun getFileSize(contentResolver: ContentResolver, uri: Uri): Long? {
        return contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            cursor.moveToFirst()
            cursor.getLong(sizeIndex)
        }
    }
}