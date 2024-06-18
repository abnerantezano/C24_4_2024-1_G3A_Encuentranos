package com.ambrosio.josue.tutorial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.models.ServicioProveedorModel

class ServicioProveedorAdapter(private val viewType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var serviciosProveedores: List<ServicioProveedorModel> = emptyList()

    private val VIEW_TYPE_UNO = 1
    private val VIEW_TYPE_DOS = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_UNO -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_servicio_proveedor, parent, false)
                ServicioProveedorViewHolderUno(view)
            }
            VIEW_TYPE_DOS -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mi_servicio, parent, false)
                ServicioProveedorViewHolderDos(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val servicio = serviciosProveedores[position]
        when (holder.itemViewType) {
            VIEW_TYPE_UNO -> {
                (holder as ServicioProveedorViewHolderUno).bind(servicio)
            }
            VIEW_TYPE_DOS -> {
                (holder as ServicioProveedorViewHolderDos).bind(servicio)
            }
        }
    }

    override fun getItemCount(): Int {
        return serviciosProveedores.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    fun submitList(list: List<ServicioProveedorModel>) {
        serviciosProveedores = list
        notifyDataSetChanged()
    }

    inner class ServicioProveedorViewHolderUno(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    inner class ServicioProveedorViewHolderDos(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val serviciosTextView: TextView = itemView.findViewById(R.id.tvServiciosProveedor)
        private val precioTextView: TextView = itemView.findViewById(R.id.tvPrecioServicio)

        fun bind(servicio: ServicioProveedorModel) {
            serviciosTextView.text = servicio.idServicio.nombre
            precioTextView.text = "S/ ${servicio.precio}"
        }
    }
}
