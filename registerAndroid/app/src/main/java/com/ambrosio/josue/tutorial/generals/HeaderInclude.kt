package com.ambrosio.josue.tutorial.generals

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.HeaderVolverBinding
import com.ambrosio.josue.tutorial.ui.activities.NotificacionesActivity

abstract class HeaderInclude : AppCompatActivity() {

    private lateinit var binding: HeaderVolverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupHeader() {
        val headerView = findViewById<LinearLayout>(R.id.header)

        if (headerView != null) {
            binding = HeaderVolverBinding.bind(headerView)
            // Configurar clics para los iconos usando los ids
            binding.icnRegresar.setOnClickListener {
                finish()
            }
            binding.imgNotificaciones.setOnClickListener{
                val intent = Intent(this, NotificacionesActivity::class.java)
                startActivity(intent)
            }
        } else {
            throw NullPointerException("Missing required view with ID: R.id.header")
        }
    }
}
