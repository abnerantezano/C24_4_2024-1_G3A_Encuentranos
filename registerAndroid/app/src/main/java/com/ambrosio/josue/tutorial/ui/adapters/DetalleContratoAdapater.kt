package com.ambrosio.josue.tutorial.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment.ContratoEspecificoActivity

class DetalleContratoAdapater : RecyclerView.Adapter<DetalleContratoAdapater.DetalleContratoAdapaterViewHolder>() {
    private var detalleContratos: List<DetalleContratoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleContratoAdapaterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contracts, parent, false)
        return DetalleContratoAdapaterViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetalleContratoAdapaterViewHolder, position: Int) {
        val detalleContrato = detalleContratos[position]
        holder.bind(detalleContrato)
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
        private val estadoContratoTextView: TextView = itemView.findViewById(R.id.tvEstado)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val detalleContrato = detalleContratos[position]
                    val intent = Intent(itemView.context, ContratoEspecificoActivity::class.java).apply {
                        putExtra("NOMBRE_CLIENTE", "${detalleContrato.idContrato.idCliente.nombre} ${detalleContrato.idContrato.idCliente.apellidoPaterno}")
                        putExtra("NOMBRE_SERVICIO", "${detalleContrato.idServicio.nombre}")
                        putExtra("PRECIO_ACTUAL", "S/ ${detalleContrato.precioActual}")
                        putExtra("ESTADO_SERVICIO", "${detalleContrato.idContrato.estado}")
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun bind(detalleContrato: DetalleContratoModel) {
            val nombre = detalleContrato.idContrato.idCliente.nombre
            val apellido = detalleContrato.idContrato.idCliente.apellidoPaterno
            val precio = detalleContrato.precioActual.toString()
            nombreClienteTextView.text = "$nombre $apellido"
            nombreServicioTextView.text = detalleContrato.idServicio.nombre
            precioServicioTextView.text = "S/ $precio"
            estadoContratoTextView.text = detalleContrato.idContrato.estado
        }
    }
}
