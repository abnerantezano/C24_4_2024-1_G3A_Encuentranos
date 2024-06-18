package com.ambrosio.josue.tutorial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.models.ServicioProveedorModel

class MIServicioAdapater : RecyclerView.Adapter<MIServicioAdapater.ServicioProveedorViewHolderDos>() {

    private var servicio: List<ServicioProveedorModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioProveedorViewHolderDos {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mi_servicio, parent, false)
        return ServicioProveedorViewHolderDos(view)
    }

    override fun onBindViewHolder(holder: ServicioProveedorViewHolderDos, position: Int) {
        val servicio = servicio[position]
        holder.bind(servicio)
    }

    override fun getItemCount(): Int {
        return servicio.size
    }

    fun submitList(list: List<ServicioProveedorModel>) {
        servicio = list
        notifyDataSetChanged()
    }

    inner class ServicioProveedorViewHolderDos(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val serviciosTextView: TextView = itemView.findViewById(R.id.tvServiciosProveedor)
        private val precioTextView: TextView = itemView.findViewById(R.id.tvPrecioServicio)

        fun bind(servicio: ServicioProveedorModel) {
            serviciosTextView.text = servicio.idServicio.nombre
            precioTextView.text = "S/ ${servicio.precio}"
        }
    }
}