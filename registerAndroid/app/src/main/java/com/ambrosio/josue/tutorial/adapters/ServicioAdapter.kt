package com.ambrosio.josue.tutorial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.models.ServicioModel

class ServicioAdapter : RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder>() {

    private var servicios: List<ServicioModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_servicio, parent, false)
        return ServicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {
        val servicio = servicios[position]
        holder.bind(servicio)
    }

    override fun getItemCount(): Int {
        return servicios.size
    }

    fun submitList(list: List<ServicioModel>) {
        servicios = list
        notifyDataSetChanged()
    }

    inner class ServicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.textNombreServicio)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.textDescripcionServicio)

        fun bind(servicio: ServicioModel) {
            nombreTextView.text = servicio.nombre
            descripcionTextView.text = servicio.descripcion
        }
    }
}
