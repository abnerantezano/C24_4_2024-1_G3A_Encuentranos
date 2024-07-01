package com.ambrosio.josue.tutorial.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambrosio.josue.tutorial.databinding.ActivityNotificacionesBinding
import com.ambrosio.josue.tutorial.ui.adapters.NotificacionesAdapter
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.NotificacionViewModel

class NotificacionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificacionesBinding
    private val notificacionViewModel: NotificacionViewModel by viewModels()
    private val inicioViewModel: InicioViewModel by viewModels()
    private lateinit var notificacionesAdapter: NotificacionesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        inicioViewModel.verificarAutenticacionUsuario()

        inicioViewModel.idTipo.observe(this, Observer { idTipo ->
            when (idTipo) {
                1 -> {
                    inicioViewModel.obtenerIdCliente()
                    inicioViewModel.idCliente.observe(this, Observer { idCliente ->
                        if (idCliente != null) {
                            notificacionViewModel.listarNotificacionesPorIdCliente(idCliente)
                        }
                    })
                }
                2 -> {
                    inicioViewModel.obtenerIdProveedor()
                    inicioViewModel.idProveedor.observe(this, Observer { idProveedor ->
                        if (idProveedor != null) {
                            notificacionViewModel.listarNotificacionesPorIdProveedor(idProveedor)
                            notificacionViewModel.obtenerDetalleContratoNotificaciones(idProveedor)
                        }
                    })
                }
            }
        })

        notificacionViewModel.notificaciones.observe(this, Observer { notificaciones ->
            notificacionViewModel.detallesContrato.observe(this, Observer { detallesContrato ->
                notificacionesAdapter.submitList(notificaciones, detallesContrato)
            })
        })
    }

    private fun setupRecyclerView() {
        notificacionesAdapter = NotificacionesAdapter()
        binding.recyclerViewNotificaciones.apply {
            layoutManager = LinearLayoutManager(this@NotificacionesActivity)
            adapter = notificacionesAdapter
        }
    }
}
