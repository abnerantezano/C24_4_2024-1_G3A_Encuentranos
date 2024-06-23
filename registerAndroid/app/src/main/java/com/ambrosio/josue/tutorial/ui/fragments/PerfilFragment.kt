package com.ambrosio.josue.tutorial.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.databinding.FragmentPerfilBinding
import com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment.InformacionPersonalActivity
import com.ambrosio.josue.tutorial.ui.activities.LoginActivity
import com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment.CambiarContrasenaActivity
import com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment.MiContratoActivity
import com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment.MiServicioActivity
import com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment.PerfilActivity
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleSignInClient: GoogleSignInClient
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.VISIBLE
        binding.informacion.visibility = View.GONE
        binding.opciones.visibility = View.GONE
        binding.cerrarSesion.visibility = View.GONE

        viewModel.mensajeError.observe(viewLifecycleOwner, Observer { message ->
            binding.progressBar.visibility = View.GONE
            binding.informacion.visibility = View.VISIBLE
            binding.opciones.visibility = View.VISIBLE
            binding.cerrarSesion.visibility = View.VISIBLE
            showToast(message)
        })
        // Set up header information
        setupHeader()

        // Fetch and display user data
        fetchUserData()

        // Set up Google sign-in client
        setupGoogleSignInClient()

        // Set up click listeners for navigation
        setupClickListeners()

        // Observa el tipo de usuario para mostrar las opciones adecuadas
        viewModel.idTipo.observe(viewLifecycleOwner, Observer { idTipo ->
            when (idTipo) {
                1 -> {
                    binding.tvMisServicios.visibility = View.GONE
                    binding.cerrarSesion.visibility = View.VISIBLE
                }
                else -> {
                    // Manejar otro caso si es necesario
                }
            }
        })
    }

    private fun setupHeader() {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val correo = user.email
            binding.tvInformacionCorreoPersonal.text = correo
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun fetchUserData() {
        viewModel.nombreUsuario.observe(viewLifecycleOwner, Observer { nombre ->
            viewModel.apellidoPaternoUsuario.observe(viewLifecycleOwner, Observer { apellido ->
                binding.progressBar.visibility = View.GONE
                // Mostrar secciones de información y opciones cuando los datos se carguen
                binding.informacion.visibility = View.VISIBLE
                binding.opciones.visibility = View.VISIBLE
                binding.cerrarSesion.visibility = View.VISIBLE
                binding.tvInformacionPersonal.text = "$nombre $apellido"
            })
        })
        viewModel.verificarAutenticacionUsuario() // Verificar estado de autenticación
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

        binding.tvMisServicios.setOnClickListener {
            val intent = Intent(activity, MiServicioActivity::class.java)
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
