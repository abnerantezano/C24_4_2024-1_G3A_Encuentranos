package com.ambrosio.josue.tutorial.activities

import android.content.Intent
import android.os.Bundle
import com.ambrosio.josue.tutorial.databinding.ActivityInicioSesionBinding
import com.ambrosio.josue.tutorial.generals.FooterInclude
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class InicioSesionActivity: FooterInclude() {
    private lateinit var binding: ActivityInicioSesionBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Verificar si el usuario ya está autenticado
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUIWithUserDetails(currentUser)
        } else {
            // Manejar el caso donde el usuario no está autenticado
        }

        goServicios()
        setupFooter()
    }

    // Función para actualizar la UI con los detalles del usuario
    private fun updateUIWithUserDetails(user: FirebaseUser) {
        val email = user.email
        val nombreUsuario = user.displayName

        if (!nombreUsuario.isNullOrEmpty()) {
            binding.tvNombreUsuario.text = "¡Bienvenido, $nombreUsuario!"
        } else {
            binding.tvNombreUsuario.text = "¡Bienvenido!"
        }

        // Mostrar el correo electrónico del usuario
        binding.tvNombreUsuario.text = email ?: "Correo no disponible"
    }

    private fun goServicios(){
        binding.btnBuscarServicios.setOnClickListener{
            val intent = Intent(this, ServiciosListActivity::class.java)
            startActivity(intent)
        }
    }
}
