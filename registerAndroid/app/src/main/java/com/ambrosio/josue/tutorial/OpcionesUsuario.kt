package com.ambrosio.josue.tutorial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ambrosio.josue.tutorial.databinding.ActivityOpcionesUsuarioBinding
import com.ambrosio.josue.tutorial.generals.FooterInclude

class OpcionesUsuario : FooterInclude() {
    private lateinit var binding: ActivityOpcionesUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOpcionesUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupFooter() // Configura el footer
    }
}
