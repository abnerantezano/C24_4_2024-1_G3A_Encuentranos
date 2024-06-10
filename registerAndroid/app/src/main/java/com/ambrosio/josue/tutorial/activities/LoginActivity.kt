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
import androidx.lifecycle.Observer
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

        // Initialize the LoginViewModel with Factory
        loginViewModel = ViewModelProvider(this, LoginViewModel.Factory(applicationContext)).get(LoginViewModel::class.java)

        // Initialize the ActivityResultLauncher
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                loginViewModel.handleSignInResult(intent)
            }
        }

        // Set onClickListener for the SignInButton
        binding.signInButton.setOnClickListener {
            val signInIntent = loginViewModel.signIn()
            resultLauncher.launch(signInIntent)
        }

        // Set onClickListener for the register text
        binding.registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
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
            startActivity(Intent(this, CrearUsuarioActivity::class.java))
            finish()
        }

        // Observar cambios en userLiveData
        loginViewModel.userLiveData.observe(this, Observer { user ->
            updateUI(user)
        })
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, CrearUsuarioActivity::class.java).apply {
                putExtra("email", user.email)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = loginViewModel.userLiveData.value
        updateUI(currentUser)
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}
