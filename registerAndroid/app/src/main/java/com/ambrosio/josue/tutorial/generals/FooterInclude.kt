package com.ambrosio.josue.tutorial.generals

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.InicioSesion
import com.ambrosio.josue.tutorial.OpcionesUsuario
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.activities.MensajeActivity
import com.ambrosio.josue.tutorial.activities.ServicioProveedorActivity
import com.ambrosio.josue.tutorial.activities.ServiciosListActivity
import com.ambrosio.josue.tutorial.databinding.FooterBinding

abstract class FooterInclude : AppCompatActivity() {
    private var binding: FooterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupFooter() {
        val footerView = findViewById<LinearLayout>(R.id.footer)
        if (footerView != null) {
            binding = FooterBinding.bind(footerView)
            // Configurar clics para los iconos usando los ids
            binding?.iconHome?.setOnClickListener {
                val intent = Intent(this, InicioSesion::class.java)
                intent.putExtra("nombreUsuario", "Usuario")
                startActivity(intent)
            }

            binding?.iconLimpieza?.setOnClickListener {
                val intent = Intent(this, ServiciosListActivity::class.java)
                startActivity(intent)
            }

            binding?.iconTarjetra?.setOnClickListener{
                val intent = Intent(this, ServicioProveedorActivity::class.java)
                startActivity(intent)
            }

            binding?.iconChat?.setOnClickListener{
                val intent = Intent(this, MensajeActivity::class.java)
                startActivity(intent)
            }
            binding?.iconUsuario?.setOnClickListener {
                val intent = Intent(this, OpcionesUsuario::class.java)
                startActivity(intent)
            }
        } else {
            throw NullPointerException("Missing required view with ID: R.id.footer")
        }
    }
}
