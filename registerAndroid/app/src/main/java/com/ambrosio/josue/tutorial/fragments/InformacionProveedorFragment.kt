package com.ambrosio.josue.tutorial.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.databinding.FragmentInformacionProveedorBinding
import com.ambrosio.josue.tutorial.viewModels.InicioViewModel

class InformacionProveedorFragment : Fragment() {

    private var _binding: FragmentInformacionProveedorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInformacionProveedorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.nombreUsuario.observe(viewLifecycleOwner, Observer { name ->
            binding.tvNombreProveedor.text = name
        })

        viewModel.verificarAutenticacionUsuario()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
