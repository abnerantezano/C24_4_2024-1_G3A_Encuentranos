package com.ambrosio.josue.tutorial.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.ui.activities.InformacionProveedorActivity
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel

class ServicioProveedorAdapter : RecyclerView.Adapter<ServicioProveedorAdapter.ViewHolder>() {

    private var serviciosProveedores: List<ServicioProveedorModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_servicio_proveedor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val servicio = serviciosProveedores[position]
        holder.bind(servicio)
    }

    override fun getItemCount(): Int {
        return serviciosProveedores.size
    }

    fun submitList(list: List<ServicioProveedorModel>) {
        serviciosProveedores = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.tvNombreProveedor)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.tvServicio)
        private val isNegociableTextView: TextView = itemView.findViewById(R.id.tvIsNegociable)
        private val precioTextView: TextView = itemView.findViewById(R.id.tvPrecioActual)
        private val btnVerPerfil: Button = itemView.findViewById(R.id.btnVerPerfil)

        fun bind(servicio: ServicioProveedorModel) {
            nombreTextView.text = servicio.idProveedor.nombre
            descripcionTextView.text = servicio.idServicio.nombre
            isNegociableTextView.text = if (servicio.negociable) "Sí es negociable" else "No es negociable"
            precioTextView.text = servicio.precio.toString()
            btnVerPerfil.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, InformacionProveedorActivity::class.java).apply {
                    putExtra("PROVEEDOR_ID", servicio.idProveedor.idProveedor)
                }
                context.startActivity(intent)
            }
        }
    }
}
