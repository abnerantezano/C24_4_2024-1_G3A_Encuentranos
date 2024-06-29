package com.ambrosio.josue.tutorial.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.databinding.ActivityInformacionProveedorBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.ui.adapters.InformacionProveedorAdapter
import com.ambrosio.josue.tutorial.ui.adapters.ListaResenaAdapter
import com.ambrosio.josue.tutorial.ui.adapters.MiServicioAdapater
import com.ambrosio.josue.tutorial.ui.adapters.ServicioProveedorAdapter
import com.ambrosio.josue.tutorial.ui.fragments.ServiciosFragment
import com.ambrosio.josue.tutorial.ui.viewModels.DetalleCalificacionViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InformacionProveedorViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.ServicioProveedorViewModel

class InformacionProveedorActivity : HeaderInclude() {
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

        binding.progressBar.visibility = View.VISIBLE
        binding.content.visibility = View.GONE

        adapterNegociables = InformacionProveedorAdapter()
        adapterNoNegociables = InformacionProveedorAdapter()
        listaResenaAdapter = ListaResenaAdapter()

        binding.recyclerViewServiciosNegociables.adapter = adapterNegociables
        binding.recyclerViewServiciosNoNegociables.adapter = adapterNoNegociables
        binding.recyclerViewResenas.adapter = listaResenaAdapter

        val proveedorId = intent.getIntExtra("PROVEEDOR_ID", -1)

        setupHeader()
        setupObservers()
        goCrearContrato(proveedorId)

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
