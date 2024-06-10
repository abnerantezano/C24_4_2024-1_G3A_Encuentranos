package com.ambrosio.josue.tutorial.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.MainActivity
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.servicios.RetrofitClient
import com.ambrosio.josue.tutorial.databinding.ActivityCrearUsuarioBinding
import com.ambrosio.josue.tutorial.viewModels.UsuarioViewModel

class CrearUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearUsuarioBinding
    private lateinit var viewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuarioApi = RetrofitClient.usuarioApi
        viewModel = UsuarioViewModel(usuarioApi)

        val email = intent.getStringExtra("email") ?: ""

        binding.apply {
            btnEnviar.setOnClickListener {
                val contrasena = editTextContrasena.text.toString()
                val confirmarContrasena = editTextConfirmarContrasena.text.toString()

                if (contrasena != confirmarContrasena) {
                    Toast.makeText(this@CrearUsuarioActivity, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val tipoUsuarioId = if (radioGroupOfreceServicio.checkedRadioButtonId == R.id.radio_si) {
                    2 // Sí: proveedor
                } else {
                    1 // No: cliente
                }

                viewModel.crearUsuario(email, contrasena, tipoUsuarioId,
                    onSuccess = { usuarioAgregado ->
                        val intent = Intent(this@CrearUsuarioActivity, if (tipoUsuarioId == 2) RegistroProveedorActivity::class.java else MainActivity::class.java).apply {
                            putExtra("user_id", usuarioAgregado.idUsuario)
                            putExtra("email", usuarioAgregado.correo)
                        }
                        startActivity(intent)
                        finish()
                    },
                    onFailure = { mensajeError ->
                        Toast.makeText(this@CrearUsuarioActivity, mensajeError, Toast.LENGTH_SHORT).show()
                    }
                )
            }

            textRegresar.setOnClickListener {
                val intent = Intent(this@CrearUsuarioActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}