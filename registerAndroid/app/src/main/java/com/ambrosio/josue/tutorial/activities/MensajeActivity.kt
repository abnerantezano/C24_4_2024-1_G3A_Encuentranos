package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.generals.FooterInclude

class MensajeActivity : FooterInclude() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mensaje)
        setupFooter()
    }
}