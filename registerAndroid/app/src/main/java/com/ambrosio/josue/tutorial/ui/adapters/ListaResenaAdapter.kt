package com.ambrosio.josue.tutorial.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.DetalleCalificacionModel
import com.ambrosio.josue.tutorial.data.models.ServicioModel

class ListaResenaAdapter : RecyclerView.Adapter<ListaResenaAdapter.ListaResenaViewHolder>() {

    private var detalleCalificacion: List<DetalleCalificacionModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaResenaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_resenas, parent, false)
        return ListaResenaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaResenaViewHolder, position: Int) {
        val detalleCalificacion = detalleCalificacion[position]
        holder.bind(detalleCalificacion)
    }

    override fun getItemCount(): Int {
        return detalleCalificacion.size
    }

    fun submitList(list: List<DetalleCalificacionModel>) {
        detalleCalificacion = list
        notifyDataSetChanged()
    }

    inner class ListaResenaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreClienteTextView: TextView = itemView.findViewById(R.id.tvNombreCliente)
        private val calificacionTextView: TextView = itemView.findViewById(R.id.tvCalificacionProveedor)
        private val ServicioTextView: TextView = itemView.findViewById(R.id.tvServicio)
        private val comentarioClienteTextView: TextView = itemView.findViewById(R.id.tvComentarioCliente)

        fun bind(detalleCalificacion: DetalleCalificacionModel) {
            nombreClienteTextView.text = detalleCalificacion.idCalificacion.cliente.nombre
            calificacionTextView.text = detalleCalificacion.idCalificacion.numero.toString()
            ServicioTextView.text = detalleCalificacion.idServicio.nombre
            comentarioClienteTextView.text = detalleCalificacion.idCalificacion.comentario
        }
    }
}
