package com.ambrosio.josue.tutorial
// Importa las clases necesarias
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ambrosio.josue.tutorial.models.ServicioModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiciosDisponibles : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var servicioAdapter: ServicioAdapter
}
