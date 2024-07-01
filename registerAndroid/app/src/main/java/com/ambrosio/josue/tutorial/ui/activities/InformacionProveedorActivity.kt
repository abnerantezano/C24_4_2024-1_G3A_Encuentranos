package com.ambrosio.josue.tutorial.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
import com.ambrosio.josue.tutorial.databinding.ActivityInformacionProveedorBinding
import com.ambrosio.josue.tutorial.ui.adapters.InformacionProveedorAdapter
import com.ambrosio.josue.tutorial.ui.adapters.ListaResenaAdapter
import com.ambrosio.josue.tutorial.ui.viewModels.ChatViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.DetalleCalificacionViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InformacionProveedorViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.ServicioProveedorViewModel
import com.squareup.picasso.Picasso

class InformacionProveedorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformacionProveedorBinding
    private val informacionProveedorViewModel: InformacionProveedorViewModel by viewModels()
    private val servicioProveedorViewModel: ServicioProveedorViewModel by viewModels()
    private val detalleCalificacionViewModel: DetalleCalificacionViewModel by viewModels()
    private val viewModel: InicioViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var adapterNegociables: InformacionProveedorAdapter
    private lateinit var adapterNoNegociables: InformacionProveedorAdapter
    private lateinit var listaResenaAdapter: ListaResenaAdapter
    private var chatId: Int = -1
    private var proveedorId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformacionProveedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE
        binding.content.visibility = View.GONE

        adapterNegociables = InformacionProveedorAdapter()
        adapterNoNegociables = InformacionProveedorAdapter()
        listaResenaAdapter = ListaResenaAdapter()

        binding.recyclerViewServiciosNegociables.adapter = adapterNegociables
        binding.recyclerViewServiciosNoNegociables.adapter = adapterNoNegociables
        binding.recyclerViewResenas.adapter = listaResenaAdapter

        proveedorId = intent.getIntExtra("PROVEEDOR_ID", -1)

        setupObservers()

        informacionProveedorViewModel.obtenerDatosProveedorPorId(proveedorId)
        servicioProveedorViewModel.obtenerServicioProveedorNegociables(proveedorId)
        servicioProveedorViewModel.obtenerServicioProveedorNoNegociables(proveedorId)
        detalleCalificacionViewModel.listarResenasDeProveedor(proveedorId)

        viewModel.idTipo.observe(this, Observer { idTipo ->
            when (idTipo) {
                2 -> {
                    binding.progressBar.visibility = View.GONE
                    binding.content.visibility = View.VISIBLE
                    binding.linearOpciones.visibility = View.GONE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.content.visibility = View.VISIBLE
                    binding.linearOpciones.visibility = View.VISIBLE
                }
            }
        })

        viewModel.verificarAutenticacionUsuario()
        viewModel.obtenerIdCliente()

        binding.btnEnviarMensaje.setOnClickListener {
            // Crear el chat si no se ha creado todavía
            if (chatId == -1) {
                chatViewModel.crearChat(viewModel.idCliente.value ?: -1, proveedorId)
                val intent = Intent(this, MensajeDeUsuariosActivity::class.java)
                intent.putExtra("chat_id", chatId)
                intent.putExtra("proveedor_id", proveedorId)
                startActivity(intent)
            } else {
                // Si el chat ya está creado, continuar con el envío de mensajes
                val intent = Intent(this, MensajeDeUsuariosActivity::class.java)
                intent.putExtra("chat_id", chatId)
                intent.putExtra("proveedor_id", proveedorId)
                startActivity(intent)
            }
        }

        binding.btnContrato.setOnClickListener {
            val intent = Intent(this, CrearContratoActivity::class.java)
            intent.putExtra("PROVEEDOR_ID", proveedorId)
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        informacionProveedorViewModel.proveedor.observe(this, Observer { proveedor ->
            proveedor?.let {
                binding.apply {
                    tvNombreProveedor.text = "${proveedor.nombre} ${proveedor.apellidoPaterno} ${proveedor.apellidoMaterno}"
                    ratingBarProveedor.rating = proveedor.calificacionPromedio.toFloat()
                    val descripcion = proveedor.descripcion.takeIf { it?.isNotBlank() ?: false } ?: "No hay descripción"

                    tvDescripcionProveedor.text = descripcion

                    proveedor.idUsuario?.let { usuario ->
                        tvCorreoProveedor.text = usuario.correo
                        val imgUsuario = binding.imgCliente
                        Picasso.get().load(usuario.imagenUrl).into(imgUsuario)

                    } ?: run {
                        tvCorreoProveedor.text = "Correo no disponible"
                    }

                    proveedor.celular?.let {
                        tvCelularProveedor.text = it
                    } ?: run {
                        tvCelularProveedor.text = "Celular no disponible"
                    }
                }
            }
        })

        servicioProveedorViewModel.listaServiciosProveedoresNegociables.observe(this, Observer { servicios ->
            if (servicios.isNullOrEmpty()) {
                binding.tvServcioNegociable.visibility = View.GONE
                adapterNegociables.submitList(emptyList())
            } else {
                binding.tvServcioNegociable.visibility = View.VISIBLE
                adapterNegociables.submitList(servicios)
            }
        })

        servicioProveedorViewModel.listaServiciosProveedoresNoNegociables.observe(this, Observer { servicios ->
            if (servicios.isNullOrEmpty()) {
                binding.tvServcioNoNegociable.visibility = View.GONE
                adapterNoNegociables.submitList(emptyList())
            } else {
                binding.tvServcioNoNegociable.visibility = View.VISIBLE
                adapterNoNegociables.submitList(servicios)
            }
        })

        detalleCalificacionViewModel.listarDetalleCalificacionPorIdProveedorYIdServicio.observe(this, Observer { detalleCalificacion ->
            detalleCalificacion?.let {
                listaResenaAdapter.submitList(detalleCalificacion)
            } ?: run {
                listaResenaAdapter.submitList(emptyList())
            }
        })

        // Observar el ID del chat creado
        chatViewModel.idChat.observe(this, Observer { idChat ->
            if (idChat != null && idChat > 0) {
                chatId = idChat
                binding.btnEnviarMensaje.visibility = View.VISIBLE
            } else {
                Log.e("InformacionProveedorActivity", "No se ha creado el chat correctamente")
                // Puedes manejar la situación si no se ha creado el chat
            }
        })
    }
}
