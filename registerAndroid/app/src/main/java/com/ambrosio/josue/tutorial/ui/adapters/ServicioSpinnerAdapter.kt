package com.ambrosio.josue.tutorial.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel

class ServicioSpinnerAdapter(context: Context, servicios: List<ServicioProveedorModel>)
    : ArrayAdapter<ServicioProveedorModel>(context, android.R.layout.simple_spinner_item, servicios) {

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        (view as TextView).text = getItem(position)?.idServicio?.nombre ?: ""
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        (view as TextView).text = getItem(position)?.idServicio?.nombre ?: ""
        return view
    }
}
