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
import androidx.navigation.fragment.findNavController
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.FragmentInicioBinding
import com.ambrosio.josue.tutorial.generals.HeaderPrincipal
import com.ambrosio.josue.tutorial.ui.activities.InformacionProveedorActivity
import com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment.MiServicioActivity
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

        viewModel.verificarAutenticacionUsuario()
        clienteOproveedor()
        irServicios()
        irServiciosProveedor()
    }

    private fun clienteOproveedor(){
        viewModel.idTipo.observe(viewLifecycleOwner, Observer { idTipo ->
            when (idTipo) {
                1 -> {
                    viewModel.nombreUsuario.observe(viewLifecycleOwner, Observer { name ->
                        binding.progressBar.visibility = View.GONE
                        binding.content.visibility = View.VISIBLE
                        binding.linearForCliente.visibility = View.VISIBLE
                        binding.linearForProveedor.visibility = View.GONE
                        binding.tvNombreUsuario.text = name
                    })
                }
                2->{
                    viewModel.nombreUsuario.observe(viewLifecycleOwner, Observer { name ->
                        binding.progressBar.visibility = View.GONE
                        binding.content.visibility = View.VISIBLE
                        binding.linearForCliente.visibility = View.GONE
                        binding.linearForProveedor.visibility = View.VISIBLE
                        binding.tvNombreUsuario.text = name
                    })
                }
            }
        })

        viewModel.mensajeError.observe(viewLifecycleOwner, Observer { message ->
            binding.progressBar.visibility = View.GONE
            binding.content.visibility = View.VISIBLE
            showToast(message)
        })
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun irServicios() {
        binding.btnBuscarServicios.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_serviciosFragment)
        }
    }

    private fun irServiciosProveedor() {
        binding.btnAnadirServicios.setOnClickListener {
            val intent = Intent(context, MiServicioActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
