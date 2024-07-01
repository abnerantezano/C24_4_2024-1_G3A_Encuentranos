package com.ambrosio.josue.tutorial.generals

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.HeaderBinding
import com.ambrosio.josue.tutorial.ui.activities.NotificacionesActivity

class HeaderPrincipal(private val rootView: View) {

    private lateinit var binding: HeaderBinding

    fun setupHeader() {
        val headerView = rootView.findViewById<LinearLayout>(R.id.header)

        if (headerView != null) {
            binding = HeaderBinding.bind(headerView)
            binding.imgNotificaciones.setOnClickListener {
                val intent = Intent(rootView.context, NotificacionesActivity::class.java)
                rootView.context.startActivity(intent)
            }
        } else {
            throw NullPointerException("Missing required view with ID: R.id.header")
        }
    }
}
