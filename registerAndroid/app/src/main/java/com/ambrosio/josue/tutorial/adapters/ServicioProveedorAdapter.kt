package com.ambrosio.josue.tutorial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.models.ServicioProveedorModel

class ServicioProveedorAdapter : RecyclerView.Adapter<ServicioProveedorAdapter.ServicioProveedorViewHolder>() {
    private var serviciosProveedores: List<ServicioProveedorModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioProveedorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_servicio_proveedor, parent, false)
        return ServicioProveedorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicioProveedorViewHolder, position: Int) {
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

    inner class ServicioProveedorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.tvNombreProveedor)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.tvServicio)
        private val isNegociableTextView: TextView = itemView.findViewById(R.id.tvIsNegociable)
        private val PrecioTextView: TextView = itemView.findViewById(R.id.tvPrecioActual)

        fun bind(servicio: ServicioProveedorModel) {
            nombreTextView.text = servicio.idProveedor.nombre
            descripcionTextView.text = servicio.idServicio.nombre
            isNegociableTextView.text = if (servicio.negociable) "Sí es negociable" else "No es negociable"
            PrecioTextView.text = servicio.precio.toString()

        }
    }
}