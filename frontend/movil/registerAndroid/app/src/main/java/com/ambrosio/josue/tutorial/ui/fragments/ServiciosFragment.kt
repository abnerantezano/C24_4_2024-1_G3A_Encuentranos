package com.ambrosio.josue.tutorial.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambrosio.josue.tutorial.databinding.FragmentServiciosBinding
import com.ambrosio.josue.tutorial.generals.HeaderPrincipal
import com.ambrosio.josue.tutorial.ui.adapters.ServicioAdapter
import com.ambrosio.josue.tutorial.ui.viewModels.ServiciosListViewModel

class ServiciosFragment : Fragment() {

    private var _binding: FragmentServiciosBinding? = null
    private val binding get() = _binding!!
    private lateinit var serviciosListViewModel: ServiciosListViewModel
    private lateinit var adapter: ServicioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServiciosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el encabezado
        val headerPrincipal = HeaderPrincipal(binding.root)
        headerPrincipal.setupHeader()

        // Mostrar el ProgressBar al iniciar la vista
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewServicios.visibility = View.GONE

        serviciosListViewModel = ServiciosListViewModel()
        serviciosListViewModel.obtenerServicios()

        adapter = ServicioAdapter()

        binding.recyclerViewServicios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewServicios.adapter = adapter

        observeValues()
    }

    private fun observeValues() {
        serviciosListViewModel.listaServicios.observe(viewLifecycleOwner, Observer { servicios ->
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewServicios.visibility = View.VISIBLE
            adapter.submitList(servicios)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
