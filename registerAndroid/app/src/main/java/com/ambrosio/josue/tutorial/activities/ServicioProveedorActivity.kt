package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambrosio.josue.tutorial.adapters.ServicioProveedorAdapter
import com.ambrosio.josue.tutorial.databinding.ActivityServicioProveedorBinding
import com.ambrosio.josue.tutorial.models.ServicioProveedorModel
import com.ambrosio.josue.tutorial.viewModels.ServicioProveedorViewModel

class ServicioProveedorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServicioProveedorBinding
    private val servicioProveedorViewModel: ServicioProveedorViewModel by viewModels()
    private lateinit var adapter: ServicioProveedorAdapter
    private var allServicios = listOf<ServicioProveedorModel>()

}
