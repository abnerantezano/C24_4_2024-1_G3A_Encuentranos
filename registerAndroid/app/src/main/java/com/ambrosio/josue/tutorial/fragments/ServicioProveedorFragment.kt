package com.ambrosio.josue.tutorial.fragments
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.adapters.ServicioProveedorAdapter
import com.ambrosio.josue.tutorial.databinding.FragmentServicioProveedorBinding
import com.ambrosio.josue.tutorial.models.ServicioProveedorModel
import com.ambrosio.josue.tutorial.viewModels.ServicioProveedorViewModel

class ServicioProveedorFragment : Fragment() {

    private var _binding: FragmentServicioProveedorBinding? = null
    private val binding get() = _binding!!
    private lateinit var servicioProveedorViewModel: ServicioProveedorViewModel
    private lateinit var adapter: ServicioProveedorAdapter
    private var allServicios = listOf<ServicioProveedorModel>()
    private var currentTextFilter = ""
    private var minPriceFilter = 0.0
    private var maxPriceFilter = Double.MAX_VALUE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServicioProveedorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewProveedores.visibility = View.GONE

        servicioProveedorViewModel = ServicioProveedorViewModel()
        servicioProveedorViewModel.obtenerServiciosProveedor()

        adapter = ServicioProveedorAdapter()
        binding.recyclerViewProveedores.adapter = adapter
        binding.recyclerViewProveedores.layoutManager = LinearLayoutManager(requireContext())

        observeValues()

        binding.edtBusqueda.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                currentTextFilter = s.toString()
                applyFilters()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.imgFiltro.setOnClickListener {
            showFilterDialog()
        }
    }

    private fun observeValues() {
        servicioProveedorViewModel.listaServiciosProveedores.observe(viewLifecycleOwner, Observer { servicios ->
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewProveedores.visibility = View.VISIBLE
            allServicios = servicios
            applyFilters()
        })
    }

    private fun applyFilters() {
        val filteredList = allServicios.filter { servicio ->
            servicio.idServicio.nombre.contains(currentTextFilter, ignoreCase = true) ||
                    servicio.idProveedor.nombre.contains(currentTextFilter, ignoreCase = true)
        }.filter { servicio ->
            servicio.precio in minPriceFilter..maxPriceFilter
        }
        adapter.submitList(filteredList)
    }

    private fun showFilterDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_filtro_precio, null)
        val etMinPrice = dialogView.findViewById<EditText>(R.id.etMinPrice)
        val etMaxPrice = dialogView.findViewById<EditText>(R.id.etMaxPrice)
        val btnApplyFilter = dialogView.findViewById<Button>(R.id.btnApplyFilter)

        etMinPrice.setText(if (minPriceFilter == 0.0) "" else minPriceFilter.toString())
        etMaxPrice.setText(if (maxPriceFilter == Double.MAX_VALUE) "" else maxPriceFilter.toString())

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        btnApplyFilter.setOnClickListener {
            minPriceFilter = etMinPrice.text.toString().toDoubleOrNull() ?: 0.0
            maxPriceFilter = etMaxPrice.text.toString().toDoubleOrNull() ?: Double.MAX_VALUE
            applyFilters()
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
