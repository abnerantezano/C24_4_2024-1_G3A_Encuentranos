package com.ambrosio.josue.tutorial.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.ambrosio.josue.tutorial.databinding.ActivityLoginBinding
import com.ambrosio.josue.tutorial.viewModels.LoginViewModel
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el LoginViewModel con Factory
        loginViewModel = ViewModelProvider(this, LoginViewModel.Factory(applicationContext)).get(LoginViewModel::class.java)

        // Inicializar el ActivityResultLauncher
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                loginViewModel.handleSignInResult(intent)
            }
        }

        // Set onClickListener para el botón de inicio de sesión
        binding.signInButton.setOnClickListener {
            val signInIntent = loginViewModel.signIn()
            resultLauncher.launch(signInIntent)
        }

        // Set onClickListener para el texto de registro
        binding.registerText.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        loginViewModel.inputsError.observe(this) {
            Toast.makeText(this, "Ingrese los datos completos", Toast.LENGTH_SHORT).show()
        }
        loginViewModel.authError.observe(this) {
            Toast.makeText(this, "Error usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
        loginViewModel.loginSuccess.observe(this) {
            startActivity(Intent(this, InicioSesionActivity::class.java))
            finish()
        }

        // Observar cambios en userLiveData
        loginViewModel.userLiveData.observe(this) { user ->
            updateUI(user)
        }

        // Observar respuesta del backend
        loginViewModel.backendResponse.observe(this) { response ->
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // El usuario está autenticado, continuar con la siguiente actividad
            startActivity(Intent(this, InicioSesionActivity::class.java))
            finish()
        }
    }
}
