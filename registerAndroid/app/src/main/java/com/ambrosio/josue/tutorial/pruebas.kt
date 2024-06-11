package com.ambrosio.josue.tutorial

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ambrosio.josue.tutorial.models.DistritoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class pruebas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pruebas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the spinner in the layout
        val distritoSpinner: Spinner = findViewById(R.id.distritoSpinner)

        // Call the API to list districts
        RetrofitClient.distritoApi.listarDistritos().enqueue(object : Callback<List<DistritoModel>> {
            override fun onResponse(call: Call<List<DistritoModel>>, response: Response<List<DistritoModel>>) {
                if (response.isSuccessful) {
                    val distritos = response.body() ?: emptyList()
                    val distritoNames = distritos.map { it.nombre }
                    val adapter = ArrayAdapter(this@pruebas, android.R.layout.simple_spinner_item, distritoNames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    distritoSpinner.adapter = adapter
                } else {
                    Toast.makeText(this@pruebas, "Failed to retrieve districts", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<DistritoModel>>, t: Throwable) {
                Toast.makeText(this@pruebas, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
