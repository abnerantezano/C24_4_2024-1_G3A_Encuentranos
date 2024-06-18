package com.ambrosio.josue.tutorial.generals

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.FooterBinding
import com.ambrosio.josue.tutorial.fragments.MensajeFragment
import com.ambrosio.josue.tutorial.fragments.InicioFragment
import com.ambrosio.josue.tutorial.fragments.PerfilFragment
import com.ambrosio.josue.tutorial.fragments.ServicioProveedorFragment
import com.ambrosio.josue.tutorial.fragments.ServiciosFragment

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
                val intent = Intent(this, InicioFragment::class.java)
                intent.putExtra("nombreUsuario", "Usuario")
                startActivity(intent)
            }

            binding?.iconLimpieza?.setOnClickListener {
                val intent = Intent(this, ServiciosFragment::class.java)
                startActivity(intent)
            }

            binding?.iconTarjetra?.setOnClickListener{
                val intent = Intent(this, ServicioProveedorFragment::class.java)
                startActivity(intent)
            }

            binding?.iconChat?.setOnClickListener{
                val intent = Intent(this, MensajeFragment::class.java)
                startActivity(intent)
            }
            binding?.iconUsuario?.setOnClickListener {
                val intent = Intent(this, PerfilFragment::class.java)
                startActivity(intent)
            }
        } else {
            throw NullPointerException("Missing required view with ID: R.id.footer")
        }
    }
}
