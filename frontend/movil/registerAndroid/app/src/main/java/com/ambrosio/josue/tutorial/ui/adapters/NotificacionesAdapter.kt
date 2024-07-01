package com.ambrosio.josue.tutorial.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import com.ambrosio.josue.tutorial.data.models.NotificacionModel
import com.ambrosio.josue.tutorial.ui.activities.AceptarOCancelarContratoActivity
import com.ambrosio.josue.tutorial.ui.activities.CalificarActivity

class NotificacionesAdapter : RecyclerView.Adapter<NotificacionesAdapter.NotificacionViewHolder>() {

    private var notificaciones: List<NotificacionModel> = emptyList()
    private var detallesContrato: List<DetalleContratoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificacionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notificaciones, parent, false)
        return NotificacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificacionViewHolder, position: Int) {
        val notificacion = notificaciones[position]
        holder.bind(notificacion)
    }

    override fun getItemCount(): Int {
        return notificaciones.size
    }

    fun submitList(notificacionesList: List<NotificacionModel>, detallesContratoList: List<DetalleContratoModel>) {
        notificaciones = notificacionesList
        detallesContrato = detallesContratoList
        notifyDataSetChanged()
    }

    inner class NotificacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreCliente: TextView = itemView.findViewById(R.id.tvNombreCliente)
        private val nuevoContrato: TextView = itemView.findViewById(R.id.tvNuevoContrato)
        private val menasje: TextView = itemView.findViewById(R.id.tvMensaje)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val notificacion = notificaciones[position]
                    val detalleContrato = detallesContrato.find { it.idContrato.idContrato == notificacion.idContrato.idContrato }

                    if (notificacion.estado == "no visto") {
                        detalleContrato?.let {
                            val intent = Intent(itemView.context, AceptarOCancelarContratoActivity::class.java).apply {
                                putExtra("ID_CONTRATO", notificacion.idContrato.idContrato)
                                putExtra("ID_CLIENTE", notificacion.idContrato.idCliente.idCliente)
                                putExtra("ID_SERVICIO", detalleContrato.idServicio.idServicio)
                                putExtra("ID_PROVEEDOR", notificacion.idProveedor.idProveedor)
                                putExtra("NOMBRE_CLIENTE", "${notificacion.idCliente.nombre} ${notificacion.idCliente.apellidoPaterno}")
                                putExtra("NOMBRE_SERVICIO", it.idServicio.nombre)
                                putExtra("PRECIO_ACTUAL", "S/ ${it.precioActual}")
                                putExtra("ESTADO_SERVICIO", notificacion.idContrato.estado)
                            }
                            itemView.context.startActivity(intent)
                        }
                    }
                    if (notificacion.idContrato.estado == "Finalizado") {
                        detalleContrato?.let {
                            val intent = Intent(itemView.context, CalificarActivity::class.java).apply {
                                putExtra("ID_CONTRATO", notificacion.idContrato.idContrato)
                                putExtra("ID_CLIENTE", notificacion.idContrato.idCliente.idCliente)
                                putExtra("ID_PROVEEDOR", notificacion.idProveedor.idProveedor)
                                putExtra("ID_SERVICIO", detalleContrato.idServicio.idServicio)
                            }
                            itemView.context.startActivity(intent)
                        }
                    }
                }
            }
        }

        fun bind(notificacion: NotificacionModel) {
            nombreCliente.text = "${notificacion.idCliente.nombre} ${notificacion.idCliente.apellidoPaterno} ${notificacion.idCliente.apellidoMaterno}"
            nuevoContrato.text = notificacion.titulo
            menasje.text = notificacion.mensaje

            if (notificacion.idContrato.estado == "Finalizado"){
                itemView.isClickable = true
                itemView.alpha = 1.0f
            }
            else if (notificacion.estado == "visto") {
                itemView.isClickable = false
                itemView.alpha = 0.5f
            } else {
                itemView.isClickable = true
                itemView.alpha = 1.0f
            }
        }
    }
}
