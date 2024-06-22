package com.ambrosio.josue.tutorial.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ambrosio.josue.tutorial.databinding.ActivityRegistroBinding
import com.ambrosio.josue.tutorial.ui.viewModels.RegistroViewModel
import com.google.firebase.auth.FirebaseUser

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var registroViewModel: RegistroViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the RegisterViewModel with Factory
        registroViewModel = ViewModelProvider(this, RegistroViewModel.Factory(applicationContext)).get(
            RegistroViewModel::class.java)

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                registroViewModel.handleSignInResult(intent)
            }
        }

        goLoginActivitiy()
        goRegistrarUsuario()

        observeViewModel()
    }

    private fun observeViewModel() {
        registroViewModel.inputsError.observe(this) {
            Toast.makeText(this, "Ingrese los datos completos", Toast.LENGTH_SHORT).show()
        }
        registroViewModel.authError.observe(this) {
            Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show()
        }
        registroViewModel.registerSuccess.observe(this) {
            val user = registroViewModel.userLiveData.value
            updateUI(user)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, CrearUsuarioActivity::class.java).apply {
                putExtra("email", user.email)
            }
            startActivity(intent)
            finish() // Close the current activity to prevent navigating back to it
        }
    }

    private fun goLoginActivitiy(){
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close the current activity to prevent navigating back to it
        }
    }

    private fun goRegistrarUsuario(){
        binding.signInButton.setOnClickListener {
            val signInIntent = registroViewModel.signIn()
            resultLauncher.launch(signInIntent)
        }
    }
}
