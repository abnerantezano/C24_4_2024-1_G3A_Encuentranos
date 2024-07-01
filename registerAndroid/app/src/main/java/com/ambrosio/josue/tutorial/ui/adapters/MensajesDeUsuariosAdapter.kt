package com.ambrosio.josue.tutorial.ui.adapters

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.MensajeModel
import java.text.SimpleDateFormat
import java.util.*

class MensajesDeUsuariosAdapter : RecyclerView.Adapter<MensajesDeUsuariosAdapter.MensajeViewHolder>() {

    private var mensajes: List<MensajeModel> = emptyList()
    private var idUsuarioConectado: Int = -1

    fun setIdUsuarioConectado(idUsuario: Int) {
        idUsuarioConectado = idUsuario
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensajes_de_usuarios, parent, false)
        return MensajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val mensaje = mensajes[position]
        holder.bind(mensaje)
    }

    override fun getItemCount(): Int {
        return mensajes.size
    }

    fun submitList(list: List<MensajeModel>) {
        mensajes = list
        notifyDataSetChanged()
    }

    inner class MensajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fechaTextView: TextView = itemView.findViewById(R.id.tvFecha)
        private val mensajeUsuarioTextView: TextView = itemView.findViewById(R.id.tvMensaje)
        private val layoutMensaje: LinearLayout = itemView.findViewById(R.id.layoutMensaje)
        private val linearContexto: LinearLayout = itemView.findViewById(R.id.linearContexto)

        @SuppressLint("ResourceAsColor")
        fun bind(mensaje: MensajeModel) {
            // Convertir fechaCreacion de String a Date
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val fechaDate = dateFormat.parse(mensaje.fechaCreacion)

            // Formatear la fecha al formato deseado: dd-MM-yyyy HH:mm
            val dateFormatFormatted = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            val fechaFormateada = dateFormatFormatted.format(fechaDate)

            fechaTextView.text = fechaFormateada
            mensajeUsuarioTextView.text = mensaje.mensaje

            if (mensaje.idEmisor.idUsuario == idUsuarioConectado) {
                layoutMensaje.gravity = Gravity.END
                fechaTextView.gravity = Gravity.END
                mensajeUsuarioTextView.gravity = Gravity.END
                linearContexto.gravity = Gravity.END
                mensajeUsuarioTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                mensajeUsuarioTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.skin))
            } else {
                layoutMensaje.gravity = Gravity.START
                fechaTextView.gravity = Gravity.START
                mensajeUsuarioTextView.gravity = Gravity.START
                linearContexto.gravity = Gravity.START
                mensajeUsuarioTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                mensajeUsuarioTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.grayGradiente))
            }
        }
    }
}