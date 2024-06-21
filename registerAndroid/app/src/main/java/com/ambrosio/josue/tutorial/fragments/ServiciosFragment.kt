package com.ambrosio.josue.tutorial.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.adapters.ServicioAdapter
import com.ambrosio.josue.tutorial.databinding.ActivityServiciosListBinding
import com.ambrosio.josue.tutorial.databinding.FragmentServiciosBinding
import com.ambrosio.josue.tutorial.viewModels.ServiciosListViewModel

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

        // Mostrar el ProgressBar al iniciar la vista
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewServicios.visibility = View.GONE

        serviciosListViewModel = ServiciosListViewModel()

        serviciosListViewModel.obtenerServicios()

        adapter = ServicioAdapter()

        observeValues()
    }

    private fun observeValues() {
        serviciosListViewModel.listaServicios.observe(viewLifecycleOwner, Observer { servicios ->
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewServicios.visibility = View.VISIBLE
            adapter.submitList(servicios)
            binding.recyclerViewServicios.adapter = adapter
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
