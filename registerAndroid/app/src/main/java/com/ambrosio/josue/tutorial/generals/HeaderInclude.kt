package com.ambrosio.josue.tutorial.generals

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.ActivityHeaderRegresarBinding

abstract class HeaderInclude : AppCompatActivity() {

    private lateinit var binding: ActivityHeaderRegresarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupHeader() {
        val headerView = findViewById<LinearLayout>(R.id.header)
        if (headerView != null) {
            binding = ActivityHeaderRegresarBinding.bind(headerView)
            // Configurar clics para los iconos usando los ids
            binding.icnRegresar.setOnClickListener {
                finish() // Regresar a la actividad anterior en la pila de actividades
            }
        } else {
            throw NullPointerException("Missing required view with ID: R.id.header")
        }
    }
}
