package com.ambrosio.josue.tutorial.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel

class InformacionProveedorAdapter : RecyclerView.Adapter<InformacionProveedorAdapter.InformacionProveedorViewHolder>() {

    private var servicios: List<ServicioProveedorModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformacionProveedorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_servicio_negociable, parent, false)
        return InformacionProveedorViewHolder(view)
    }

    override fun onBindViewHolder(holder: InformacionProveedorViewHolder, position: Int) {
        val servicio = servicios[position]
        holder.bind(servicio)
    }

    override fun getItemCount(): Int = servicios.size

    fun submitList(list: List<ServicioProveedorModel>) {
        servicios = list
        notifyDataSetChanged()
    }

    inner class InformacionProveedorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvServiciosProveedor: TextView = itemView.findViewById(R.id.tvServiciosProveedor)
        private val tvPrecioServicio: TextView = itemView.findViewById(R.id.tvPrecioServicio)

        init {
            // Asegúrate de que itemView y los TextViews no sean nulos
            if (itemView == null || tvServiciosProveedor == null || tvPrecioServicio == null) {
                throw IllegalArgumentException("itemView or child views cannot be null")
            }
        }

        fun bind(servicio: ServicioProveedorModel) {
            tvServiciosProveedor.text = servicio.idServicio.nombre
            tvPrecioServicio.text = "S/ ${servicio.precio}"
        }
    }

}
