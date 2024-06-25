package com.ambrosio.josue.tutorial.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import com.ambrosio.josue.tutorial.ui.activities.AceptarOCancelarContratoActivity
import com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment.ContratoEspecificoActivity

class NotificacionesAdapter : RecyclerView.Adapter<NotificacionesAdapter.NotificacionViewHolder>() {

    private var detallesContrato: List<DetalleContratoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificacionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notificaciones, parent, false)
        return NotificacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificacionViewHolder, position: Int) {
        val detalleContrato = detallesContrato[position]
        holder.bind(detalleContrato)
    }

    override fun getItemCount(): Int {
        return detallesContrato.size
    }

    fun submitList(list: List<DetalleContratoModel>) {
        detallesContrato = list
        notifyDataSetChanged()
    }

    inner class NotificacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreClienteTextView: TextView = itemView.findViewById(R.id.tvNombreCliente)
        private val mensajeTextView: TextView = itemView.findViewById(R.id.tvMensaje)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val detalleContrato = detallesContrato[position]
                    val intent = Intent(itemView.context, AceptarOCancelarContratoActivity::class.java).apply {
                        putExtra("ID_CONTRATO", detalleContrato.idContrato.idContrato)
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
            nombreClienteTextView.text = detalleContrato.idContrato.idCliente.nombre
            mensajeTextView.text = "Esperando tu Respuesta"
        }
    }
}
