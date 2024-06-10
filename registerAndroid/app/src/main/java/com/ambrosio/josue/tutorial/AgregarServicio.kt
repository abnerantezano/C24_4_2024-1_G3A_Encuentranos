package com.ambrosio.josue.tutorial

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.models.DistritoModel
import com.ambrosio.josue.tutorial.models.ProveedorModel
import com.ambrosio.josue.tutorial.models.ServicioModel
import com.ambrosio.josue.tutorial.models.TipoUsuarioModel
import com.ambrosio.josue.tutorial.models.UsuarioModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarServicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_servicio)
        val apiService = RetrofitClient.apiService
        val spnServicio: Spinner = findViewById(R.id.spnServicio)
        val precioEditText: EditText = findViewById(R.id.edit_text_precio)
        val negociableSpinner: Spinner = findViewById(R.id.negociableSpinner)
        val agregarButton: Button = findViewById(R.id.button_agregar)

        // Crear un objeto Usuario predeterminado
        val defaultUsuario = UsuarioModel(0, TipoUsuarioModel(0, ""), "", "", null, true, "")

        // Obtener la lista de servicios
        apiService.listarServicios().enqueue(object : Callback<List<ServicioModel>> {
            override fun onResponse(call: Call<List<ServicioModel>>, response: Response<List<ServicioModel>>) {
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

            override fun onFailure(call: Call<List<ServicioModel>>, t: Throwable) {
                Toast.makeText(this@AgregarServicio, "Error en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // Configurar el spinner de negociable
        val negociableAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf("Sí", "No"))
        negociableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        negociableSpinner.adapter = negociableAdapter

        agregarButton.setOnClickListener {
            val precio = precioEditText.text.toString().toDoubleOrNull()
            val negociable = negociableSpinner.selectedItem.toString() == "Sí"
            val idProveedor = intent.getIntExtra("proveedor_id", -1)

            if (precio == null) {
                Toast.makeText(this, "Por favor, introduce un precio válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val servicioSeleccionado = spnServicio.selectedItem.toString()
            val servicioId = (spnServicio.selectedItemPosition + 1) // Ajusta según la lógica de tu backend

            val nuevoServicioProveedor = ServicioProveedor(
                idServicio = ServicioModel(servicioId, servicioSeleccionado, ""),
                idProveedor = ProveedorModel(idProveedor, defaultUsuario, DistritoModel(0, ""), "", "", "", "", "", "", "2023-12-12", 0.0, "", ""),
                precio = precio,
                negociable = negociable
            )

            apiService.agregarServicioProveedor(listOf(nuevoServicioProveedor)).enqueue(object : Callback<List<ServicioProveedor>> {
                override fun onResponse(call: Call<List<ServicioProveedor>>, response: Response<List<ServicioProveedor>>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AgregarServicio, "Servicio agregado exitosamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AgregarServicio, "Error al agregar el servicio", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ServicioProveedor>>, t: Throwable) {
                    // Imprimir el error en el Logcat
                    Log.e("AgregarServicio", "Error en la conexión: ${t.message}", t)

                    // Mostrar un mensaje de error al usuario
                    Toast.makeText(this@AgregarServicio, "Error en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

// Imprimir los datos que se están enviando en el cuerpo de la solicitud
            Log.d("AgregarServicio", "Datos enviados: $nuevoServicioProveedor")


        }
    }
}