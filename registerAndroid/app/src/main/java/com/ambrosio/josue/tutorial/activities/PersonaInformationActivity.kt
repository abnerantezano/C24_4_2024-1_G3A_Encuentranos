package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.generals.FooterInclude

class PersonaInformationActivity : FooterInclude() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persona_information)
        setupFooter()
    }
}