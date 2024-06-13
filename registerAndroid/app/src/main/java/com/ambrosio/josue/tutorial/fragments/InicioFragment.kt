package com.ambrosio.josue.tutorial.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ambrosio.josue.tutorial.activities.ServiciosListActivity
import com.ambrosio.josue.tutorial.databinding.ActivityInicioSesionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class InicioFragment : Fragment() {

    private var _binding: ActivityInicioSesionBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityInicioSesionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Verificar si el usuario ya está autenticado
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUIWithUserDetails(currentUser)
        } else {
            // Manejar el caso donde el usuario no está autenticado
        }

        goServicios()
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

    private fun goServicios() {
        binding.btnBuscarServicios.setOnClickListener {
            val intent = Intent(activity, ServiciosListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
