package com.ambrosio.josue.tutorial.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ambrosio.josue.tutorial.MainActivity
import com.ambrosio.josue.tutorial.databinding.ActivityLoginBinding
import com.ambrosio.josue.tutorial.viewModels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Inicializa el ViewModel con un Factory
        loginViewModel = ViewModelProvider(this, LoginViewModel.Factory(applicationContext)).get(LoginViewModel::class.java)

        // Inicializa el ActivityResultLauncher
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                loginViewModel.handleSignInResult(intent)
                val user = auth.currentUser
                user?.getIdToken(true)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result?.token
                        verificarCorreoEnBackend(user.email, idToken)
                    } else {
                        Toast.makeText(this, "Error al obtener el token", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Configura OnClickListener para el botón de inicio de sesión
        binding.signInButton.setOnClickListener {
            val signInIntent = loginViewModel.signIn()
            resultLauncher.launch(signInIntent)
        }

        // Configura OnClickListener para el texto de registro
        binding.registerText.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        observeViewModel()
    }

    private fun verificarCorreoEnBackend(email: String?, idToken: String?) {
        if (email == null || idToken == null) return

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://192.168.100.13:4000/usuario/verificar/$email")
            .addHeader("Authorization", "Bearer $idToken")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("LoginActivity", "Error de red: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Error de red", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    val user = auth.currentUser
                    runOnUiThread {
                        if (user != null) {
                            updateUI(user)
                        }
                    }
                } else {
                    Log.e("LoginActivity", "Error del servidor: ${response.code}")
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Usuario no registrado", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, RegistroActivity::class.java))
                    }
                }
            }
        })
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun observeViewModel() {
        loginViewModel.inputsError.observe(this) {
            Toast.makeText(this, "Ingrese los datos completos", Toast.LENGTH_SHORT).show()
        }
        loginViewModel.authError.observe(this) {
            Toast.makeText(this, "Error usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
        loginViewModel.loginSuccess.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
