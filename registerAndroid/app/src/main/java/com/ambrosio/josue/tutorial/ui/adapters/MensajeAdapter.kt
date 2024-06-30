package com.ambrosio.josue.tutorial.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.ChatModel

class MensajeAdapter(private val onItemClick: (ChatModel) -> Unit) :
    RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

    private var mensajes: List<ChatModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensaje, parent, false)
        return MensajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val mensaje = mensajes[position]
        holder.bind(mensaje)
        holder.itemView.setOnClickListener { onItemClick(mensaje) }
    }

    override fun getItemCount(): Int {
        return mensajes.size
    }

    fun submitList(list: List<ChatModel>) {
        mensajes = list
        notifyDataSetChanged()
    }

    inner class MensajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreUsuarioTextView: TextView = itemView.findViewById(R.id.tvNombreUsuario)
        private val mensajeUsuarioTextView: TextView = itemView.findViewById(R.id.tvMensajeUsuario)

        fun bind(mensaje: ChatModel) {
            nombreUsuarioTextView.text = mensaje.idProveedor.nombre
            mensajeUsuarioTextView.text = mensaje.idCliente.nombre
        }
    }
}
