package com.ambrosio.josue.tutorial.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.databinding.ActivityInformacionProveedorBinding
import com.ambrosio.josue.tutorial.ui.adapters.InformacionProveedorAdapter
import com.ambrosio.josue.tutorial.ui.adapters.ListaResenaAdapter
import com.ambrosio.josue.tutorial.ui.adapters.MiServicioAdapater
import com.ambrosio.josue.tutorial.ui.adapters.ServicioProveedorAdapter
import com.ambrosio.josue.tutorial.ui.fragments.ServiciosFragment
import com.ambrosio.josue.tutorial.ui.viewModels.DetalleCalificacionViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InformacionProveedorViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.ServicioProveedorViewModel

class InformacionProveedorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformacionProveedorBinding
    private val informacionProveedorViewModel: InformacionProveedorViewModel by viewModels()
    private val servicioProveedorViewModel: ServicioProveedorViewModel by viewModels()
    private val detalleCalificacionViewModel: DetalleCalificacionViewModel by viewModels()
    private val viewModel: InicioViewModel by viewModels()
    private lateinit var adapterNegociables: InformacionProveedorAdapter
    private lateinit var adapterNoNegociables: InformacionProveedorAdapter
    private lateinit var listaResenaAdapter: ListaResenaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformacionProveedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterNegociables = InformacionProveedorAdapter()
        adapterNoNegociables = InformacionProveedorAdapter()
        listaResenaAdapter = ListaResenaAdapter()

        binding.recyclerViewServiciosNegociables.adapter = adapterNegociables
        binding.recyclerViewServiciosNoNegociables.adapter = adapterNoNegociables
        binding.recyclerViewResenas.adapter = listaResenaAdapter

        val proveedorId = intent.getIntExtra("PROVEEDOR_ID", -1)

        setupObservers()
        goCrearContrato(proveedorId)

        informacionProveedorViewModel.obtenerDatosProveedorPorId(proveedorId)
        servicioProveedorViewModel.obtenerServicioProveedorNegociables(proveedorId)
        servicioProveedorViewModel.obtenerServicioProveedorNoNegociables(proveedorId)
        detalleCalificacionViewModel.listarResenasDeProveedor(proveedorId)

        viewModel.idTipo.observe(this, Observer { idTipo ->
            when (idTipo) {
                2 -> {
                    binding.linearOpciones.visibility = View.GONE
                }
                else -> {
                    // Añadir manejo para otros casos si es necesario
                    binding.linearOpciones.visibility = View.VISIBLE
                }
            }
        })

        viewModel.verificarAutenticacionUsuario()

    }

    private fun setupObservers() {
        informacionProveedorViewModel.proveedor.observe(this, Observer { proveedor ->
            proveedor?.let {
                binding.apply {
                    tvNombreProveedor.text = "${proveedor.nombre} ${proveedor.apellidoPaterno} ${proveedor.apellidoMaterno}"
                    tvCalificacionProveedor.text = proveedor.calificacionPromedio.toString()
                    tvCorreoProveedor.text = proveedor.idUsuario.correo
                    tvCelularProveedor.text = proveedor.celular
                    tvDescripcionProveedor.text = proveedor.descripcion
                }
            }
        })

        servicioProveedorViewModel.listaServiciosProveedoresNegociables.observe(this, Observer { servicios ->
            servicios?.let {
                adapterNegociables.submitList(servicios)
            } ?: run {
                // Manejar el caso donde la lista de servicios negociables sea null o vacía
                adapterNegociables.submitList(emptyList())
            }
        })

        servicioProveedorViewModel.listaServiciosProveedoresNoNegociables.observe(this, Observer { servicios ->
            servicios?.let {
                adapterNoNegociables.submitList(servicios)
            } ?: run {
                // Manejar el caso donde la lista de servicios no negociables sea null o vacía
                adapterNoNegociables.submitList(emptyList())
            }
        })

        detalleCalificacionViewModel.listarDetalleCalificacionPorIdProveedorYIdServicio.observe(this, Observer { detalleCalificacion ->
            detalleCalificacion?.let{
                listaResenaAdapter.submitList(detalleCalificacion)
            } ?: run {
                listaResenaAdapter.submitList(emptyList())
            }
        })
    }

    private fun goCrearContrato(proveedorId: Int){
        binding.btnContrato.setOnClickListener{
            val intent = Intent(this, CrearContratoActivity::class.java)
            intent.putExtra("PROVEEDOR_ID", proveedorId)
            startActivity(intent)
        }
    }
}
