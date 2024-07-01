package com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
import com.ambrosio.josue.tutorial.databinding.ActivityPerfilBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.ui.adapters.FileRequestBody
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.PerfilViewModel
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PerfilActivity : HeaderInclude() {

    private lateinit var binding: ActivityPerfilBinding
    private val perfilViewModel: PerfilViewModel by viewModels()
    private val inicioViewModel: InicioViewModel by viewModels()
    private var selectedImageFileUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    private val registerForFiles =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    selectedImageFileUri = uri
                    binding.imgFoto.setImageURI(selectedImageFileUri)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        inicioViewModel.verificarAutenticacionUsuario()
        setupObservers()
        setupHeader()

        binding.imgFoto.setOnClickListener {
            openFileChooser()
        }

        binding.btnGuardar.setOnClickListener {
            val descripcion = binding.tvDescripcionUsuario.text.toString()
            // Guardar la imagen seleccionada en tu sistema de almacenamiento (ejemplo: Firebase Storage)

            // Actualizar la descripción en función del tipo de usuario (cliente o proveedor)
            when (inicioViewModel.idTipo.value) {
                1 -> {
                    inicioViewModel.obtenerIdCliente()
                }
                2 -> {
                    inicioViewModel.obtenerIdProveedor()
                }
            }

            selectedImageFileUri?.let { uri ->
                val realPath = getRealPathFromURI(uri)
                if (realPath != null) {
                    val imageFile = File(realPath)
                    if (imageFile.exists()) {
                        inicioViewModel.usuario.observe(this, Observer { usuarioModelo ->
                            usuarioModelo?.let {
                                Log.e("PerfilActivity", "El archivo que se está enviando es${imageFile}")
                                uploadFileToS3AndServer(it.idUsuario, it, imageFile)
                            }
                        })
                    } else {
                        Log.e("PerfilActivity", "El archivo de imagen no existe")
                    }
                } else {
                    Log.e("PerfilActivity", "No se pudo obtener la ruta real de la imagen")
                }
            } ?: run {
                Log.e("PerfilActivity", "selectedImageFileUri es nulo")
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

    private fun setupUI() {
        binding.progressBar.visibility = View.VISIBLE
        binding.imgFoto.visibility = View.GONE
        binding.linearDescripcion.visibility = View.GONE
        binding.linearCurriculum.visibility = View.GONE
        binding.btnGuardar.visibility = View.GONE
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedImageFileUri = data.data
            binding.imgFoto.setImageURI(selectedImageFileUri)
        }
    }

    private fun getRealPathFromURI(contentUri: Uri): String? {
        var realPath: String? = null
        try {
            contentResolver.openInputStream(contentUri)?.use { inputStream ->
                val tempFile = createTempFile(suffix = ".jpg")
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                realPath = tempFile.absolutePath
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return realPath
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

    private fun uploadFileToS3AndServer(idUsuario: Int, usuario: UsuarioModel, file: File) {
        val requestBody = FileRequestBody(contentResolver, Uri.fromFile(file)) { bytesWritten, contentLength ->
            val progress = (100 * bytesWritten / contentLength).toInt()
            runOnUiThread {
                // Update progress UI
                binding.progressBar.progress = progress
            }
        }

        perfilViewModel.uploadFileToServer(idUsuario, usuario, file.name, requestBody)
    }
}