package com.ambrosio.josue.tutorial.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ambrosio.josue.tutorial.databinding.ActivityRegisterBinding
import com.ambrosio.josue.tutorial.viewModels.RegisterViewModel
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the RegisterViewModel with Factory
        registerViewModel = ViewModelProvider(this, RegisterViewModel.Factory(applicationContext)).get(RegisterViewModel::class.java)

        // Initialize the ActivityResultLauncher
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                registerViewModel.handleSignInResult(intent)
            }
        }

        // Set onClickListener for the "Iniciar Sesión" TextView
        binding.loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optional: close the current activity if you don't want users to navigate back to it
        }

        // Set onClickListener for the Google Sign-In button
        binding.signInButton.setOnClickListener {
            val signInIntent = registerViewModel.signIn()
            resultLauncher.launch(signInIntent)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        registerViewModel.inputsError.observe(this) {
            Toast.makeText(this, "Ingrese los datos completos", Toast.LENGTH_SHORT).show()
        }
        registerViewModel.authError.observe(this) {
            Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show()
        }
        registerViewModel.registerSuccess.observe(this) {
            val user = registerViewModel.userLiveData.value
            updateUI(user)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, CrearUsuarioActivity::class.java).apply {
                putExtra("email", user.email)
            }
            startActivity(intent)
            finish() // Optional: close the current activity if you don't want users to navigate back to it
        }
    }
}
