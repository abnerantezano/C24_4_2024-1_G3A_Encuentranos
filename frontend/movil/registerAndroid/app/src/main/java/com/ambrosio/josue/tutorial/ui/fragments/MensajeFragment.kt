package com.ambrosio.josue.tutorial.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambrosio.josue.tutorial.ui.activities.MensajeDeUsuariosActivity
import com.ambrosio.josue.tutorial.ui.adapters.MensajeAdapter
import com.ambrosio.josue.tutorial.databinding.FragmentMensajeBinding
import com.ambrosio.josue.tutorial.generals.HeaderPrincipal
import com.ambrosio.josue.tutorial.ui.viewModels.ChatViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel

class MensajeFragment : Fragment() {
    private var _binding: FragmentMensajeBinding? = null
    private val binding get() = _binding!!
    private val chatViewModel: ChatViewModel by viewModels()
    private val viewModel: InicioViewModel by viewModels()
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

        // Configurar el encabezado
        val headerPrincipal = HeaderPrincipal(binding.root)
        headerPrincipal.setupHeader()

        // Inicializar adaptador y configurar clics
        adapter = MensajeAdapter { chat ->
            val intent = Intent(requireContext(), MensajeDeUsuariosActivity::class.java).apply {
                putExtra("chat_id", chat.idChat)
                putExtra("proveedor_id", chat.idProveedor.idProveedor)
            }
            startActivity(intent)
        }

        viewModel.verificarAutenticacionUsuario()

        // Mostrar el ProgressBar al iniciar la vista
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewMensajes.visibility = View.GONE

        binding.recyclerViewMensajes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMensajes.adapter = adapter

        // Fetch messages
        chatViewModel.obtenerChats()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.idTipo.observe(viewLifecycleOwner, Observer { idTipo ->
            when (idTipo) {
                1 -> {
                    viewModel.obtenerIdCliente()
                    viewModel.idCliente.observe(viewLifecycleOwner, Observer { idCliente ->
                        if (idCliente != null) {
                            chatViewModel.obtenerChatsIdCliente(idCliente)
                        }
                        chatViewModel.listachatsIdCliente.observe(viewLifecycleOwner, Observer { chats ->
                            if (chats != null) {
                                binding.progressBar.visibility = View.GONE
                                binding.recyclerViewMensajes.visibility = View.VISIBLE
                                adapter.submitList(chats)
                            } else {
                                Log.d("MensajeFragment", "No mensajes received")
                            }
                        })
                    })
                }
                2 -> {
                    viewModel.obtenerIdProveedor()
                    viewModel.idProveedor.observe(viewLifecycleOwner, Observer { idProveedor ->
                        if (idProveedor != null) {
                            chatViewModel.obtenerChatsIdProveedor(idProveedor)
                        }
                        chatViewModel.listachatsIdProveedor.observe(viewLifecycleOwner, Observer { chats ->
                            if (chats != null) {
                                binding.progressBar.visibility = View.GONE
                                binding.recyclerViewMensajes.visibility = View.VISIBLE
                                adapter.submitList(chats)
                            } else {
                                Log.d("MensajeFragment", "No mensajes received")
                            }
                        })
                    })
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
