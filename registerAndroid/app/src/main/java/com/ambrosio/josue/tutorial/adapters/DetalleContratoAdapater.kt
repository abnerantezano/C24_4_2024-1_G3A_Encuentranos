package com.ambrosio.josue.tutorial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.proyecto.encuentranos.models.DetalleContratoModel

class DetalleContratoAdapater : RecyclerView.Adapter<DetalleContratoAdapater.DetalleContratoAdapaterViewHolder>() {
    private var detalleContratos: List<DetalleContratoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleContratoAdapaterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contracts, parent, false)
        return DetalleContratoAdapaterViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetalleContratoAdapaterViewHolder, position: Int) {
        val detalleContratos = detalleContratos[position]
        holder.bind(detalleContratos)
    }

    override fun getItemCount(): Int {
        return detalleContratos.size
    }

    fun submitList(list: List<DetalleContratoModel>) {
        detalleContratos = list
        notifyDataSetChanged()
    }

    inner class DetalleContratoAdapaterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreClienteTextView: TextView = itemView.findViewById(R.id.tvNombreCliente)
        private val nombreServicioTextView: TextView = itemView.findViewById(R.id.tvNombreServicio)
        private val precioServicioTextView: TextView = itemView.findViewById(R.id.tvPrecioActual)
        private val EstadoContratoTextView: TextView = itemView.findViewById(R.id.tvEstado)

        fun bind(detalleContrato: DetalleContratoModel) {
            nombreClienteTextView.text = detalleContrato.idContrato.idCliente.nombre
            nombreServicioTextView.text = detalleContrato.idServicio.nombre
            precioServicioTextView.text = detalleContrato.precioActual.toString()
            EstadoContratoTextView.text = detalleContrato.idContrato.estado
        }
    }
}