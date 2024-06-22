package com.ambrosio.josue.tutorial.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.MainActivity
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.databinding.ActivityCrearUsuarioBinding
import com.ambrosio.josue.tutorial.ui.viewModels.UsuarioViewModel

class CrearUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearUsuarioBinding
    private lateinit var viewModel: UsuarioViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCrearUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViewModel()
        setupListeners()

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    private fun initializeViewModel() {
        val usuarioApi = RetrofitClient.usuarioApi
        viewModel = UsuarioViewModel(usuarioApi)
    }

    private fun setupListeners() {
        binding.apply {
            btnEnviar.setOnClickListener { crearUsuario() }
            tvRegresar.setOnClickListener { navigateToLogin() }
        }
    }

    private fun crearUsuario() {
        val email = intent.getStringExtra("email") ?: ""
        val contrasena = binding.edtContrasena.text.toString()
        val confirmarContrasena = binding.edtConfirmarContrasena.text.toString()

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
                // Guardar el user_id en SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putInt("user_id", usuarioAgregado.idUsuario)
                editor.putString("email", usuarioAgregado.correo)
                editor.putInt("tipo_id", usuarioAgregado.idTipo.idTipo)
                editor.apply()

                navigateToNextActivity(tipoUsuarioId, usuarioAgregado.idUsuario, usuarioAgregado.correo)
            },
            onFailure = { mensajeError ->
                Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun navigateToNextActivity(tipoUsuarioId: Int, userId: Int, email: String) {
        val intent = Intent(this,
            if (tipoUsuarioId == 2 || tipoUsuarioId == 1)
                RegistroProveedorOClienteActivity::class.java else MainActivity::class.java).apply {
            putExtra("user_id", userId)
            putExtra("email", email)
            putExtra("tipo_id", tipoUsuarioId)
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
