package com.ambrosio.josue.tutorial.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambrosio.josue.tutorial.adapters.MensajeAdapter
import com.ambrosio.josue.tutorial.databinding.FragmentMensajeBinding
import com.ambrosio.josue.tutorial.viewModels.MensajeViewModel

class MensajeFragment : Fragment() {
    private var _binding: FragmentMensajeBinding? = null
    private val binding get() = _binding!!
    private val mensajeViewModel: MensajeViewModel by viewModels()
    private lateinit var adapter: MensajeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMensajeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mostrar el ProgressBar al iniciar la vista
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewMensajes.visibility = View.GONE

        adapter = MensajeAdapter()
        binding.recyclerViewMensajes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMensajes.adapter = adapter

        // Fetch messages
        mensajeViewModel.obtenerMensajes()

        observeValues()
    }

    private fun observeValues() {
        mensajeViewModel.listaMensaje.observe(viewLifecycleOwner, Observer { mensajes ->
            if (mensajes != null) {
                binding.progressBar.visibility = View.GONE
                binding.recyclerViewMensajes.visibility = View.VISIBLE
                adapter.submitList(mensajes)
                binding.recyclerViewMensajes.adapter = adapter
            } else {
                Log.d("MensajeFragment", "No mensajes received")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
