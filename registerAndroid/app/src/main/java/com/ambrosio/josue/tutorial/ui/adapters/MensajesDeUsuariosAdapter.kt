package com.ambrosio.josue.tutorial.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.MensajeModel

class MensajesDeUsuariosAdapter : RecyclerView.Adapter<MensajesDeUsuariosAdapter.MensajeViewHolder>() {

    private var mensajes: List<MensajeModel> = emptyList()

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
        private val nombreUsuarioTextView: TextView = itemView.findViewById(R.id.tvNombrePrimerUsuario)
        private val mensajeUsuarioTextView: TextView = itemView.findViewById(R.id.tvMensaje)
        private val nombreSegundoUsuarioTextView: TextView = itemView.findViewById(R.id.tvNombreSegundoUsuario)
        private val mensajeSegundoUsuarioTextView: TextView = itemView.findViewById(R.id.tvMensaje2)

        fun bind(mensaje: MensajeModel) {
            if (mensaje.idEmisor != null) {
                nombreUsuarioTextView.text = mensaje.idEmisor.correo
                mensajeUsuarioTextView.text = mensaje.mensaje
            }
            if (mensaje.idReceptor != null) {
                nombreSegundoUsuarioTextView.text = mensaje.idReceptor.correo
                mensajeSegundoUsuarioTextView.text = mensaje.mensaje
            }
        }
    }
}
