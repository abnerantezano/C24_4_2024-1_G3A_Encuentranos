package com.ambrosio.josue.tutorial

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Hello : AppCompatActivity() {

    private lateinit var profileImage: CircleImageView
    private lateinit var helloText: TextView
    private lateinit var userDetailsTextView: TextView
    private lateinit var correoEditText: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        auth = FirebaseAuth.getInstance()

        helloText = findViewById(R.id.tvHello)
        profileImage = findViewById(R.id.profile_image)
        userDetailsTextView = findViewById(R.id.userDetailsTextView)
        correoEditText = findViewById(R.id.editTextText1)

        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            Glide
                .with(this)
                .load(currentUser.photoUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(profileImage)
            helloText.text = "Hello, ${currentUser.email}"
            correoEditText.setText(currentUser.email) // Establecer el correo electrónico en el EditText
        }

        // Set onClickListener for the sign out button
        findViewById<Button>(R.id.btnTest).setOnClickListener {
            auth.signOut()
            finish()
        }

        // Retrofit Call to list TipoUsuario
        val userSpinner: Spinner = findViewById(R.id.userSpinner)
        val apiService = RetrofitClient.apiService
        val call = apiService.listarTipoUsuarios()

        call.enqueue(object : Callback<List<TipoUsuario>> {
            override fun onResponse(call: Call<List<TipoUsuario>>, response: Response<List<TipoUsuario>>) {
                if (response.isSuccessful) {
                    val usuarios = response.body()
                    val userDetailsList = usuarios?.map { user -> "${user.id}: ${user.nombre}, ${user.permisos}" }
                    val adapter = ArrayAdapter(this@Hello, android.R.layout.simple_spinner_item, userDetailsList!!)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    userSpinner.adapter = adapter

                    userSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            userDetailsTextView.text = userDetailsList[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            userDetailsTextView.text = ""
                        }
                    }
                } else {
                    userDetailsTextView.text = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<TipoUsuario>>, t: Throwable) {
                userDetailsTextView.text = "Error: ${t.message}"
            }
        })

        // Button to add new Usuario
        val contrasenaEditText: EditText = findViewById(R.id.editTextText)
        val tipoUsuarioSpinner: Spinner = findViewById(R.id.userSpinner)

        findViewById<Button>(R.id.button).setOnClickListener {
            val correo = correoEditText.text.toString()
            val contrasena = contrasenaEditText.text.toString()
            val tipoUsuarioString = tipoUsuarioSpinner.selectedItem as String
            val tipoUsuarioId = tipoUsuarioString.split(":")[0].toInt()

            val apiService = RetrofitClient.apiService

            val nuevoUsuario = Usuario(0, TipoUsuario(tipoUsuarioId, "", ""), correo, contrasena, null, false)

            apiService.agregarUsuario(nuevoUsuario).enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        val usuarioAgregado = response.body()
                        userDetailsTextView.text = "Usuario agregado: ${usuarioAgregado?.correo}"
                    } else {
                        userDetailsTextView.text = "Error al agregar usuario: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    userDetailsTextView.text = "Error al agregar usuario: ${t.message}"
                }
            })
        }
    }
}
