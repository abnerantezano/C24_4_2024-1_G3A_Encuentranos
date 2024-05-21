package com.ambrosio.josue.tutorial

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        val sexoRadioGroup: RadioGroup = findViewById(R.id.radio_group_servicio)
        val enviarButton: Button = findViewById(R.id.button_enviar)

        // Obtener la lista de distritos
        apiService.listarDistritos().enqueue(object : Callback<List<Distrito>> {
            override fun onResponse(call: Call<List<Distrito>>, response: Response<List<Distrito>>) {
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

            override fun onFailure(call: Call<List<Distrito>>, t: Throwable) {
                Toast.makeText(this@RegistroProveedor, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        enviarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val apellidoPaterno = apellidoPaternoEditText.text.toString()
            val apellidoMaterno = apellidoMaternoEditText.text.toString()

            val fechaNacimientoStr = fechaNacimientoEditText.text.toString() // Obtiene la fecha de nacimiento del EditText

            val fechaNacimiento: Date? = try {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaNacimientoStr)
            } catch (e: Exception) {
                null
            }

            // Verifica si la fecha de nacimiento es null
            if (fechaNacimiento == null) {
                Toast.makeText(this, "Por favor, introduce una fecha válida en el formato yyyy-MM-dd", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Formatea la fecha de nacimiento en el formato requerido por el servidor
            val fechaNacimientoFormatted = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fechaNacimiento)

            val dni = dniEditText.text.toString()
            val celular = celularEditText.text.toString()
            val idDistrito = distritoSpinner.selectedItemPosition + 1 // Obtén el ID del distrito seleccionado del Spinner
            val sexo = if (sexoRadioGroup.checkedRadioButtonId == R.id.radio_masculino) {
                "Masculino"
            } else {
                "Femenino"
            }

            val tipoUsuario = TipoUsuario(1, "Proveedor", "") // Proporciona una instancia válida de TipoUsuario

            val nuevoProveedor = Proveedor(
                id = 0,
                idUsuario = Usuario(userId, tipoUsuario, email, "", null, false), // Usuario previamente creado
                nombre = nombre,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                sexo = sexo,
                dni = dni,
                celular = celular,
                fechaNacimiento = fechaNacimientoStr,
                idDistrito = Distrito(idDistrito, ""), // Obtén el distrito adecuado
                disponible = true, // Disponible por defecto
                calificacionPromedio = 0.0 // Calificación inicial
            )

            val apiService = RetrofitClient.apiService
            apiService.agregarProveedor(nuevoProveedor).enqueue(object : Callback<Proveedor> {
                override fun onResponse(call: Call<Proveedor>, response: Response<Proveedor>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegistroProveedor, "Proveedor registrado exitosamente", Toast.LENGTH_SHORT).show()
                        // Aquí puedes redirigir a otra actividad si es necesario
                    } else {
                        Toast.makeText(this@RegistroProveedor, "Error al registrar proveedor", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Proveedor>, t: Throwable) {
                    Toast.makeText(this@RegistroProveedor, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
