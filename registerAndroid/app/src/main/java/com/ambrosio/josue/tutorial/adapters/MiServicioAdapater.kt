package com.ambrosio.josue.tutorial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.models.ServicioProveedorModel
import com.ambrosio.josue.tutorial.activities.MiServicioActivity

class MiServicioAdapater(
    private val miServicioActivity: MiServicioActivity
) : RecyclerView.Adapter<MiServicioAdapater.ServicioProveedorViewHolder>() {

    private var servicios: List<ServicioProveedorModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioProveedorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mi_servicio, parent, false)
        return ServicioProveedorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicioProveedorViewHolder, position: Int) {
        val servicio = servicios[position]
        holder.bind(servicio)
    }

    override fun getItemCount(): Int {
        return servicios.size
    }

    fun submitList(list: List<ServicioProveedorModel>) {
        servicios = list
        notifyDataSetChanged()
    }

    fun getPositionOfService(nombreServicio: String): Int {
        return servicios.indexOfFirst { it.idServicio.nombre == nombreServicio }
    }

    inner class ServicioProveedorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvServiciosProveedor: TextView = itemView.findViewById(R.id.tvServiciosProveedor)
        private val tvPrecioServicio: TextView = itemView.findViewById(R.id.tvPrecioServicio)
        private val imgEditarServicio: ImageView = itemView.findViewById(R.id.imgEditarServicio)

        fun bind(servicio: ServicioProveedorModel) {
            tvServiciosProveedor.text = servicio.idServicio.nombre
            tvPrecioServicio.text = "S/ ${servicio.precio}"

            imgEditarServicio.setOnClickListener {
                miServicioActivity.mostrarDialogoEditarServicio(servicio)
            }
        }
    }
}
