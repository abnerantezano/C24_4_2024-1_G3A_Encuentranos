package com.ambrosio.josue.tutorial

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.models.DistritoModel
import com.ambrosio.josue.tutorial.models.ProveedorModel
import com.ambrosio.josue.tutorial.models.TipoUsuarioModel
import com.ambrosio.josue.tutorial.models.UsuarioModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistroProveedor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_proveedor)
        val apiService = RetrofitClient.apiService

        val userId = intent.getIntExtra("user_id", -1)
        val email = intent.getStringExtra("email") ?: ""

        // Referencias a las vistas
        val nombreEditText: EditText = findViewById(R.id.edit_text_nombre_completo)
        val apellidoPaternoEditText: EditText = findViewById(R.id.edit_text_apellido_paterno)
        val apellidoMaternoEditText: EditText = findViewById(R.id.edit_text_apellido_materno)
        val fechaNacimientoEditText: EditText = findViewById(R.id.edit_text_fecha_nacimiento)
        val dniEditText: EditText = findViewById(R.id.edit_text_dni)
        val celularEditText: EditText = findViewById(R.id.edit_text_celular)
        val distritoSpinner: Spinner = findViewById(R.id.distritoSpinner)
        val enviarButton: Button = findViewById(R.id.button_enviar)

        // Obtener la lista de distritos
        apiService.listarDistritos().enqueue(object : Callback<List<DistritoModel>> {
            override fun onResponse(call: Call<List<DistritoModel>>, response: Response<List<DistritoModel>>) {
                if (response.isSuccessful) {
                    val distritos = response.body()
                    if (distritos != null) {
                        // Crear un adaptador para los distritos
                        val adapter = ArrayAdapter(this@RegistroProveedor, android.R.layout.simple_spinner_item, distritos.map { it.nombre })
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                        // Asignar el adaptador al Spinner
                        distritoSpinner.adapter = adapter
                    } else {
                        Toast.makeText(this@RegistroProveedor, "Lista de distritos vacía", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegistroProveedor, "Error al obtener la lista de distritos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<DistritoModel>>, t: Throwable) {
                Toast.makeText(this@RegistroProveedor, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        enviarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val apellidoPaterno = apellidoPaternoEditText.text.toString()
            val apellidoMaterno = apellidoMaternoEditText.text.toString()
            val fechaNacimientoStr = fechaNacimientoEditText.text.toString()
            val dni = dniEditText.text.toString()
            val celular = celularEditText.text.toString()
            val idDistrito = distritoSpinner.selectedItemPosition + 1

            // Verificar si la fecha de nacimiento es válida
            val fechaNacimiento: Date? = try {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaNacimientoStr)
            } catch (e: Exception) {
                null
            }

            if (fechaNacimiento == null) {
                Toast.makeText(this, "Por favor, introduce una fecha válida en el formato yyyy-MM-dd", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tipoUsuario = TipoUsuarioModel(1, "Proveedor")
            val nuevoProveedor = ProveedorModel(
                idProveedor = 0,
                idUsuario = UsuarioModel(userId, tipoUsuario, email, "", null, true, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())),
                idDistrito = DistritoModel(idDistrito, ""),
                nombre = nombre,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                sexo = "", // Añade la opción para el sexo
                dni = dni,
                celular = celular,
                fechaNacimiento = fechaNacimientoStr,
                calificacionPromedio = 0.0,
                curriculumUrl = "", // Agrega el campo para la URL del curriculum
                fechaRegistro = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) // Agrega la fecha de registro actual
            )

            // Lógica para enviar la solicitud al servidor
            apiService.agregarProveedor(nuevoProveedor).enqueue(object : Callback<ProveedorModel> {
                override fun onResponse(call: Call<ProveedorModel>, response: Response<ProveedorModel>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegistroProveedor, "Proveedor registrado exitosamente", Toast.LENGTH_SHORT).show()

                        // Redireccionar a AgregarServicio
                        val intent = Intent(this@RegistroProveedor, AgregarServicio::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@RegistroProveedor, "Error al registrar proveedor", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ProveedorModel>, t: Throwable) {
                    Toast.makeText(this@RegistroProveedor, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
