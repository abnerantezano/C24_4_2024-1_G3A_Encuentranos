package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.databinding.ActivityCambiarContrasenaBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.viewModels.InicioViewModel

class CambiarContrasenaActivity : HeaderInclude() {

    private lateinit var binding: ActivityCambiarContrasenaBinding
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader()

        // Observa cambios en _userPassword
        viewModel.contrasenaUsuario.observe(this, Observer { password ->
            binding.edtContrasena.setText(password)
        })

        // Llama a fetchUserPassword para obtener la contraseña del usuario
        viewModel.obtenerContrasenaUsuario()

        setupHeader()
    }

}
