package com.ambrosio.josue.tutorial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearCuenta : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)
        auth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email") ?: ""

        val contrasenaEditText: EditText = findViewById(R.id.edit_text_contrasena)
        val confirmarContrasenaEditText: EditText = findViewById(R.id.edit_text_confirmar_contrasena)
        val radioGroup: RadioGroup = findViewById(R.id.radio_group_servicio)
        val botonEnviar: Button = findViewById(R.id.button_enviar)

        botonEnviar.setOnClickListener {
            val contrasena = contrasenaEditText.text.toString()
            val confirmarContrasena = confirmarContrasenaEditText.text.toString()

            // Verificar que las contraseñas coincidan
            if (contrasena != confirmarContrasena) {
                // Mostrar un mensaje de error o realizar alguna acción
                Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Obtener el tipo de usuario seleccionado
            val tipoUsuarioId = if (radioGroup.checkedRadioButtonId == R.id.radio_si) {
                1 // Si selecciona "Sí", el tipo de usuario es 1
            } else {
                2 // Si selecciona "No", el tipo de usuario es 2
            }

            // Creamos un nuevo usuario con los datos ingresados
            val nuevoUsuario = Usuario(
                id = 0, // El ID se generará automáticamente en la base de datos
                idTipo = TipoUsuario(tipoUsuarioId, "", ""), // Tipo de usuario seleccionado
                correo = email, // Correo electrónico del usuario
                contrasena = contrasena, // Contraseña ingresada por el usuario
                imageUrl = null, // La imagen puede ser nula en este punto
                eliminada = false // El usuario recién creado no está eliminado
            )

            // Llamada a Retrofit para agregar el nuevo usuario
            val apiService = RetrofitClient.apiService
            apiService.agregarUsuario(nuevoUsuario).enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        // El usuario se agregó exitosamente
                        val usuarioAgregado = response.body()
                        // Navegar a la pantalla de registro de proveedor
                        val intent = Intent(this@CrearCuenta, RegistroProveedor::class.java).apply {
                            putExtra("user_id", usuarioAgregado?.id)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        // Error al agregar el usuario
                        Toast.makeText(this@CrearCuenta, "Error al crear el usuario", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    // Error de red u otro error
                    Toast.makeText(this@CrearCuenta, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Configurar el comportamiento para el texto "Regresar"
        findViewById<TextView>(R.id.text_regresar).setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
