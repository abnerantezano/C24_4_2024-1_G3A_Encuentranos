package com.ambrosio.josue.tutorial.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambrosio.josue.tutorial.data.models.ChatModel
import com.ambrosio.josue.tutorial.data.models.TipoUsuarioModel
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
import com.ambrosio.josue.tutorial.databinding.ActivityMensajeDeUsuariosBinding
import com.ambrosio.josue.tutorial.ui.adapters.MensajesDeUsuariosAdapter
import com.ambrosio.josue.tutorial.ui.viewModels.MensajeViewModel

class MensajeDeUsuariosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMensajeDeUsuariosBinding
    private lateinit var adapter: MensajesDeUsuariosAdapter
    private val mensajeViewModel: MensajeViewModel by lazy {
        ViewModelProvider(this).get(MensajeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMensajeDeUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val chatId = intent.getIntExtra("chat_id", -1)
        val emisor = UsuarioModel(
            idUsuario = 1,  // Replace with actual ID
            idTipo = TipoUsuarioModel(1,""),  // Replace with actual type
            correo = "emisor@example.com",  // Replace with actual email
            contrasena = "password",  // Replace with actual password
            imageUrl = "http://example.com/image.jpg",  // Replace with actual URL or null
            estado = "activo",
            fechaCreacion = "2024-06-29"  // Replace with actual date
        )
        val receptor = UsuarioModel(
            idUsuario = 2,  // Replace with actual ID
            idTipo = TipoUsuarioModel(1, ""),  // Replace with actual type
            correo = "receptor@example.com",  // Replace with actual email
            contrasena = "password",  // Replace with actual password
            imageUrl = "http://example.com/image.jpg",  // Replace with actual URL or null
            estado = "activo",
            fechaCreacion = "2024-06-29"  // Replace with actual date
        )

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
        }

        binding.btnEnviar.setOnClickListener {
            val mensaje = binding.etMensaje.text.toString().trim()
            if (mensaje.isNotEmpty()) {
                mensajeViewModel.enviarMensaje(chatId, mensaje, emisor, receptor)
                binding.etMensaje.text.clear()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MensajesDeUsuariosAdapter()
        binding.recyclerViewMensajes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMensajes.adapter = adapter
    }
}
