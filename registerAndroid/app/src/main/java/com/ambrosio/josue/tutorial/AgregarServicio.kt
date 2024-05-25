package com.ambrosio.josue.tutorial

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarServicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_servicio)
        val apiService = RetrofitClient.apiService
        val spnServicio: Spinner = findViewById(R.id.spnServicio)

        // Obtener la lista de servicios
        apiService.listarServicios().enqueue(object : Callback<List<Servicio>> {
            override fun onResponse(call: Call<List<Servicio>>, response: Response<List<Servicio>>) {
                if (response.isSuccessful) {
                    val servicios = response.body()
                    if (servicios != null) {
                        // Crear un adaptador para los servicios
                        val adapter = ArrayAdapter(this@AgregarServicio, android.R.layout.simple_spinner_item, servicios.map { it.nombre })
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                        // Asignar el adaptador al Spinner
                        spnServicio.adapter = adapter
                    } else {
                        Toast.makeText(this@AgregarServicio, "Lista de servicios vacía", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@AgregarServicio, "Error al obtener la lista de servicios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Servicio>>, t: Throwable) {
                Toast.makeText(this@AgregarServicio, "Error en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
