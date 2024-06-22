package com.ambrosio.josue.tutorial.ui.activities

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
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
import com.ambrosio.josue.tutorial.data.servicios.UsuarioApi
import com.ambrosio.josue.tutorial.databinding.ActivityLoginBinding
import com.ambrosio.josue.tutorial.ui.viewModels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth
    private val usuarioApi: UsuarioApi = RetrofitClient.usuarioApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Inicializa el ViewModel con un Factory
        loginViewModel = ViewModelProvider(this, LoginViewModel.Factory(applicationContext)).get(
            LoginViewModel::class.java)

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
        binding.tvRegistrate.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        observeViewModel()
    }

    fun verificarCorreoEnBackend(email: String?, idToken: String?) {
        if (email == null || idToken == null) return

        val call = usuarioApi.verificarUsuarioPorCorreo(email)
        call.enqueue(object : Callback<UsuarioModel> {
            override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                if (response.isSuccessful) {
                    // Usuario encontrado, actualiza UI o realiza acciones necesarias
                    Log.d("InformacionProveedorVM", "Usuario encontrado: ${response.body()}")
                } else {
                    // Usuario no encontrado, maneja la respuesta del servidor
                    Log.e("InformacionProveedorVM", "Usuario no registrado: ${response.code()}")
                    // Puedes manejar el error según tus requerimientos
                }
            }

            override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                Log.e("InformacionProveedorVM", "Error en la llamada: ${t.message}")
                // Maneja el error de red aquí
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
