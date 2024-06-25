package com.ambrosio.josue.tutorial.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.FragmentInicioBinding
import com.ambrosio.josue.tutorial.generals.HeaderPrincipal
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel

class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el encabezado
        val headerPrincipal = HeaderPrincipal(binding.root)
        headerPrincipal.setupHeader()

        // Mostrar el ProgressBar al iniciar la vista
        binding.progressBar.visibility = View.VISIBLE
        binding.content.visibility = View.GONE

        viewModel.nombreUsuario.observe(viewLifecycleOwner, Observer { name ->
            binding.progressBar.visibility = View.GONE
            binding.content.visibility = View.VISIBLE
            binding.tvNombreUsuario.text = name
        })

        viewModel.mensajeError.observe(viewLifecycleOwner, Observer { message ->
            binding.progressBar.visibility = View.GONE
            binding.content.visibility = View.VISIBLE
            showToast(message)
        })

        viewModel.verificarAutenticacionUsuario()
        setupNavigation()
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupNavigation() {
        binding.btnBuscarServicios.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_serviciosFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
