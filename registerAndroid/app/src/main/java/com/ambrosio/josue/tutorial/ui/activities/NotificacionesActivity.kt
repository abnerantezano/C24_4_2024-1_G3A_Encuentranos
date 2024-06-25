package com.ambrosio.josue.tutorial.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.ActivityNotificacionesBinding
import com.ambrosio.josue.tutorial.ui.adapters.NotificacionesAdapter
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.NotificacionesViewModel

class NotificacionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificacionesBinding
    private val notificacionesViewModel: NotificacionesViewModel by viewModels()
    private val viewModel: InicioViewModel by viewModels()
    private lateinit var notificacionesAdapter: NotificacionesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificacionesAdapter = NotificacionesAdapter()
        binding.recyclerViewNotificaciones.apply {
            layoutManager = LinearLayoutManager(this@NotificacionesActivity)
            adapter = notificacionesAdapter
        }

        viewModel.obtenerIdProveedor()
        listarContratosPendientes()
        observarDatos()
    }

    private fun listarContratosPendientes(){
        viewModel.idProveedor.observe(this, Observer { idProveedor ->
            if (idProveedor != null) {
                notificacionesViewModel.obtenerDetalleContratoNotificaciones(idProveedor)
            }
        })
    }

    private fun observarDatos() {
        notificacionesViewModel.listaDetalleContrato.observe(this, Observer { lista ->
            if (lista != null) {
                notificacionesAdapter.submitList(lista)
            } else {
                Toast.makeText(this, "No se encontraron contratos pendientes", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
