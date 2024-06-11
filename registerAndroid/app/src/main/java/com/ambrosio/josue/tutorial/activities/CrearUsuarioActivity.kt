package com.ambrosio.josue.tutorial.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.MainActivity
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.databinding.ActivityCrearUsuarioBinding
import com.ambrosio.josue.tutorial.viewModels.UsuarioViewModel

class CrearUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearUsuarioBinding
    private lateinit var viewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViewModel()
        setupUI()
        setupListeners()
    }

    private fun initializeViewModel() {
        val usuarioApi = RetrofitClient.usuarioApi
        viewModel = UsuarioViewModel(usuarioApi)
    }

    private fun setupUI() {
    }

    private fun setupListeners() {
        binding.apply {
            btnEnviar.setOnClickListener { handleCreateUser() }
            textRegresar.setOnClickListener { navigateToLogin() }
        }
    }

    private fun handleCreateUser() {
        val email = intent.getStringExtra("email") ?: ""
        val contrasena = binding.editTextContrasena.text.toString()
        val confirmarContrasena = binding.editTextConfirmarContrasena.text.toString()

        if (contrasena != confirmarContrasena) {
            Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        val tipoUsuarioId = if (binding.radioGroupOfreceServicio.checkedRadioButtonId == R.id.radio_si) {
            2 // Sí: proveedor
        } else {
            1 // No: cliente
        }

        viewModel.crearUsuario(email, contrasena, tipoUsuarioId,
            onSuccess = { usuarioAgregado ->
                navigateToNextActivity(tipoUsuarioId, usuarioAgregado.idUsuario, usuarioAgregado.correo)
            },
            onFailure = { mensajeError ->
                Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun navigateToNextActivity(tipoUsuarioId: Int, userId: Int, email: String) {
        val intent = Intent(this, if (tipoUsuarioId == 2) RegistroProveedorActivity::class.java else MainActivity::class.java).apply {
            putExtra("user_id", userId)
            putExtra("email", email)
        }
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
