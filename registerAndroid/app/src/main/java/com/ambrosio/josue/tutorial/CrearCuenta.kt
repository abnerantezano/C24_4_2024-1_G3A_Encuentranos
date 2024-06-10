package com.ambrosio.josue.tutorial

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.models.TipoUsuarioModel
import com.ambrosio.josue.tutorial.models.UsuarioModel
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
        val radioGroupOfreceServicio: RadioGroup = findViewById(R.id.radio_group_ofrece_servicio)
        val botonEnviar: Button = findViewById(R.id.btnEnviar)


        botonEnviar.setOnClickListener {
            val contrasena = contrasenaEditText.text.toString()
            val confirmarContrasena = confirmarContrasenaEditText.text.toString()

            if (contrasena != confirmarContrasena) {
                Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tipoUsuarioId = if (radioGroupOfreceServicio.checkedRadioButtonId == R.id.radio_si) {
                2 // Sí: proveedor
            } else {
                1 // No: cliente
            }

            val tipoUsuario = TipoUsuarioModel(tipoUsuarioId, "") // Crear un objeto TipoUsuario con el ID
            val nuevoUsuario = UsuarioModel(
                idUsuario = 0,
                idTipo = tipoUsuario, // Pasar el objeto TipoUsuario completo
                correo = email,
                contrasena = contrasena,
                imageUrl = null,
                activo = true,
                fechaCreacion = "2024-06-03"
            )

            val apiService = RetrofitClient.apiService
            apiService.agregarUsuario(nuevoUsuario).enqueue(object : Callback<UsuarioModel> {
                override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                    if (response.isSuccessful) {
                        val usuarioAgregado = response.body()
                        val intent = Intent(this@CrearCuenta, if (tipoUsuarioId == 2) RegistroProveedor::class.java else MainActivity::class.java).apply {
                            putExtra("user_id", usuarioAgregado?.idUsuario)
                            putExtra("email", usuarioAgregado?.correo)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@CrearCuenta, "Error al crear el usuario", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                    Toast.makeText(this@CrearCuenta, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        findViewById<TextView>(R.id.text_regresar).setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

}
