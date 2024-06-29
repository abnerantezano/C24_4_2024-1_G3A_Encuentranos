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
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DetalleContratoAdapater(
    private val viewModel: InicioViewModel
) : RecyclerView.Adapter<DetalleContratoAdapater.DetalleContratoViewHolder>() {
    private var detalleContratos: List<DetalleContratoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleContratoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contracts, parent, false)
        return DetalleContratoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetalleContratoViewHolder, position: Int) {
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

    inner class DetalleContratoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tipoUsuarioTextView: TextView = itemView.findViewById(R.id.tvTipoUsuario)
        private val nombreUsuarioTextView: TextView = itemView.findViewById(R.id.tvNombreUsuario)
        private val nombreServicioTextView: TextView = itemView.findViewById(R.id.tvNombreServicio)
        private val precioServicioTextView: TextView = itemView.findViewById(R.id.tvPrecioActual)
        private val estadoContratoTextView: TextView = itemView.findViewById(R.id.tvEstado)
        private val fechaInicioTextView: TextView = itemView.findViewById(R.id.tvFechaInicio)
        private val horaInicioTextView: TextView = itemView.findViewById(R.id.tvHoraInicio)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val detalleContrato = detalleContratos[position]
                    val intent = Intent(itemView.context, ContratoEspecificoActivity::class.java).apply {
                        putExtra("NOMBRE_USUARIO", obtenerNombreUsuario(detalleContrato))
                        putExtra("TIPO_USUARIO", obtenerTipoUsuario())
                        putExtra("NOMBRE_SERVICIO", detalleContrato.idServicio.nombre)
                        putExtra("PRECIO_ACTUAL", "S/ ${detalleContrato.precioActual}")
                        putExtra("ESTADO_SERVICIO", detalleContrato.idContrato.estado)
                        putExtra("FECHA_INICIO", detalleContrato.idContrato.fechaInicio)
                        putExtra("FECHA_FIN", detalleContrato.idContrato.fechaFin)
                        putExtra("HORA_INICIO", detalleContrato.idContrato.hiServicio)
                        putExtra("HORA_FIN", detalleContrato.idContrato.hfServicio)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun bind(detalleContrato: DetalleContratoModel) {
            val sdfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val sdfOutput = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val fechaInicio = detalleContrato.idContrato.fechaInicio
            val fechaInicioFormateada = fechaInicio?.let { sdfInput.parse(it) }?.let { sdfOutput.format(it) }

            tipoUsuarioTextView.text = obtenerTipoUsuario()
            nombreUsuarioTextView.text = obtenerNombreUsuario(detalleContrato)
            nombreServicioTextView.text = detalleContrato.idServicio.nombre
            precioServicioTextView.text = "S/ ${detalleContrato.precioActual}"
            estadoContratoTextView.text = detalleContrato.idContrato.estado
            fechaInicioTextView.text = fechaInicioFormateada
            horaInicioTextView.text = detalleContrato.idContrato.hiServicio
        }

        private fun obtenerNombreUsuario(detalleContrato: DetalleContratoModel): String {
            val tipoUsuario = viewModel.idTipo.value
            return if (tipoUsuario == 1) {
                "${detalleContrato.idProveedor.nombre} ${detalleContrato.idProveedor.apellidoPaterno}"
            } else {
                "${detalleContrato.idContrato.idCliente.nombre} ${detalleContrato.idContrato.idCliente.apellidoPaterno}"
            }
        }
        private fun obtenerTipoUsuario(): String {
            val tipoUsuario = viewModel.idTipo.value
            return if (tipoUsuario == 1) {
                "Proveedor"
            } else {
                "Cliente"
            }
        }
    }
}
