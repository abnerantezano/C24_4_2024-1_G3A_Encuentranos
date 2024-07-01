package com.ambrosio.josue.tutorial.ui.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambrosio.josue.tutorial.databinding.ActivityMensajeDeUsuariosBinding
import com.ambrosio.josue.tutorial.ui.adapters.MensajesDeUsuariosAdapter
import com.ambrosio.josue.tutorial.ui.viewModels.InformacionProveedorViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.MensajeViewModel

class MensajeDeUsuariosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMensajeDeUsuariosBinding
    private lateinit var adapter: MensajesDeUsuariosAdapter
    private val infoViewModel: InformacionProveedorViewModel by viewModels()
    private val mensajeViewModel: MensajeViewModel by viewModels()
    private val viewModel: InicioViewModel by viewModels()
    private var receptorId: Int = -1
    private var idUsuarioProveedor: Int = -1
    private var idUsuarioConectado: Int = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMensajeDeUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel.verificarAutenticacionUsuario()

        viewModel.obtenerIdCliente()
        viewModel.idUsuario.observe(this, Observer { idUsuario ->
            idUsuarioConectado = idUsuario
            adapter.setIdUsuarioConectado(idUsuario)
            binding.tvIdEmisor.text = idUsuario.toString()
        })

        val chatId = intent.getIntExtra("chat_id", -1)
        val proveedorId = intent.getIntExtra("proveedor_id", -1)

        infoViewModel.obtenerIdUsuarioProveedor(proveedorId)
        infoViewModel.idUsuarioProveedor.observe(this, Observer { idUsuarioProveedor ->
            this.idUsuarioProveedor = idUsuarioProveedor
            binding.tvIdReceptor.text = idUsuarioProveedor.toString()
            actualizarCabezera(proveedorId)

            if (chatId != -1) {
                mensajeViewModel.obtenerMensajesPorIdChat(chatId)
                mensajeViewModel.listaMensajePorId.observe(this, Observer { mensajes ->
                    if (mensajes != null) {
                        adapter.submitList(mensajes)
                        binding.recyclerViewMensajes.smoothScrollToPosition(adapter.itemCount - 1)
                    } else {
                        Log.d("MensajeFragment", "No mensajes received")
                    }
                })

                // Iniciar el sondeo periódico
                mensajeViewModel.iniciarSondeo(chatId)
            }

            binding.imgEnviar.setOnClickListener {
                val mensaje = binding.edtMensaje.text.toString().trim()
                if (mensaje.isNotEmpty()) {
                    mensajeViewModel.enviarMensaje(chatId, mensaje, idUsuarioConectado, idUsuarioProveedor)
                    binding.edtMensaje.text.clear()
                }
            }
        })

        // Observar cambios en la lista de mensajes después de enviar un nuevo mensaje
        mensajeViewModel.listaMensajePorId.observe(this, Observer { mensajes ->
            mensajes?.let {
                adapter.submitList(it.toList())
                binding.recyclerViewMensajes.smoothScrollToPosition(adapter.itemCount - 1)
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = MensajesDeUsuariosAdapter()
        binding.recyclerViewMensajes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMensajes.adapter = adapter
    }

    private fun actualizarCabezera(idProveedor: Int){
        binding.tvNombreUsuario.text = idProveedor.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        mensajeViewModel.detenerSondeo() // Detener el sondeo cuando la actividad se destruya
    }
}
