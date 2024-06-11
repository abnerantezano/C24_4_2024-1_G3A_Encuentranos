package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.generals.FooterInclude

class ChangePasswordsActivity : FooterInclude() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        setupFooter()
    }
}