package com.ambrosio.josue.tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.databinding.ActivityInicioSesionBinding
import com.ambrosio.josue.tutorial.generals.FooterInclude

class InicioSesion : FooterInclude() {
    private lateinit var binding: ActivityInicioSesionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreUsuario = intent.getStringExtra("nombreUsuario")
        binding.tvNombreUsuario.text = "¡Bienvenido, $nombreUsuario!"
        setupFooter()
    }
}
