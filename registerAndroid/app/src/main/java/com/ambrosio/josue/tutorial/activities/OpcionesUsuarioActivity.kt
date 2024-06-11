package com.ambrosio.josue.tutorial.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.databinding.ActivityOpcionesUsuarioBinding
import com.ambrosio.josue.tutorial.generals.FooterInclude
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener

class OpcionesUsuarioActivity : FooterInclude() {

    private lateinit var binding: ActivityOpcionesUsuarioBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFooter()
        setupGoogleSignInClient()
        setupClickListeners()
    }

    private fun setupGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupClickListeners() {
        binding.tvPerfil.setOnClickListener {
            val intent = Intent(this, ProfilelActivity::class.java)
            startActivity(intent)
        }

        binding.tvVerInformacion.setOnClickListener {
            val intent = Intent(this, PersonaInformationActivity::class.java)
            startActivity(intent)
        }

        binding.tvCambiarContrasena.setOnClickListener {
            val intent = Intent(this, ChangePasswordsActivity::class.java)
            startActivity(intent)
        }

        binding.tvContratos.setOnClickListener {
            val intent = Intent(this, MyContractsActivity::class.java)
            startActivity(intent)
        }
        binding.tvCerrarSesion.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this, OnCompleteListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

}