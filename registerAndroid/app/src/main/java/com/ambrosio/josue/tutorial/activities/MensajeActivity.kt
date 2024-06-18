package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.generals.FooterInclude

class MensajeActivity : FooterInclude() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_mensaje)
        setupFooter()
    }
}