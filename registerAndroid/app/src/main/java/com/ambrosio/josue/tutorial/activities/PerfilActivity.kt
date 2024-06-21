package com.ambrosio.josue.tutorial.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.ActivityPerfilBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.viewModels.InicioViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult

class PerfilActivity : HeaderInclude() {

    private lateinit var binding: ActivityPerfilBinding
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        user?.getIdToken(true)
            ?.addOnCompleteListener(OnCompleteListener<GetTokenResult> { task ->
                if (task.isSuccessful) {
                    val idToken = task.result?.token
                    val email = user.email
                    if (email != null && idToken != null) {
                        viewModel.obtenerNombreUsuario(email, idToken)
                    }
                } else {
                    // Handle error -> task.exception
                    Log.e("InformacionPersonal", "Error getting token: ${task.exception}")
                }
            })
        setupObservers()
        setupHeader()
    }

    private fun setupObservers() {
        viewModel.descripcionUsuario.observe(this, Observer { descripcion ->
            descripcion?.let {
                if (it != "null") {
                    binding.tvDescripcionUsuario.setText(it)
                } else {
                    binding.tvDescripcionUsuario.setHint(R.string.escribe_tu_descripci_n)
                }
            } ?: run {
                binding.tvDescripcionUsuario.setHint(R.string.escribe_tu_descripci_n)
            }
        })
    }
}