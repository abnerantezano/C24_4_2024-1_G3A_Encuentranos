package com.ambrosio.josue.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.activities.*

class FooterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_footer_fragment, container, false)

        view.findViewById<View>(R.id.iconLimpieza).setOnClickListener {
            startActivity(Intent(requireContext(), ServiciosListActivity::class.java))
        }
        view.findViewById<View>(R.id.iconTarjeta).setOnClickListener {
            startActivity(Intent(requireContext(), ServicioProveedorActivity::class.java))
        }
        view.findViewById<View>(R.id.iconChat).setOnClickListener {
            startActivity(Intent(requireContext(), MensajeActivity::class.java))
        }
        view.findViewById<View>(R.id.iconUsuario).setOnClickListener {
            startActivity(Intent(requireContext(), OpcionesUsuarioActivity::class.java))
        }

        return view
    }
}
