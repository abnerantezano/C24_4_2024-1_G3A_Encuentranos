package com.ambrosio.josue.tutorial.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ambrosio.josue.tutorial.activities.*
import com.ambrosio.josue.tutorial.databinding.ActivityOpcionesUsuarioBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener

class PerfilFragment : Fragment() {

    private var _binding: ActivityOpcionesUsuarioBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityOpcionesUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGoogleSignInClient()
        setupClickListeners()
    }

    private fun setupGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun setupClickListeners() {
        binding.tvPerfil.setOnClickListener {
            val intent = Intent(activity, PerfilActivity::class.java)
            startActivity(intent)
        }

        binding.tvVerInformacion.setOnClickListener {
            val intent = Intent(activity, InformacionPersonalActivity::class.java)
            startActivity(intent)
        }

        binding.tvCambiarContrasena.setOnClickListener {
            val intent = Intent(activity, CambiarContrasenaActivity::class.java)
            startActivity(intent)
        }

        binding.tvContratos.setOnClickListener {
            val intent = Intent(activity, MiContratoActivity::class.java)
            startActivity(intent)
        }
        binding.tvCerrarSesion.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener(requireActivity(), OnCompleteListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
