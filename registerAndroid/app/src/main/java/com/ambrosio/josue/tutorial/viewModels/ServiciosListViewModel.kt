package com.ambrosio.josue.tutorial.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.servicios.RetrofitClient
import com.ambrosio.josue.tutorial.models.ServicioModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiciosListViewModel(
    var context: Context
): ViewModel() {

    private val servicioApi = RetrofitClient.servicioApi
    private lateinit var servicioModel: ServicioModel
    val listaServicios = MutableLiveData<List<ServicioModel>>()

    fun obtenerServicios() {
        servicioApi.listarServicios().enqueue(object : Callback<List<ServicioModel>> {
            override fun onResponse(call: Call<List<ServicioModel>>, response: Response<List<ServicioModel>>) {
                if (response.isSuccessful) {
                    listaServicios.postValue(response.body())
                } else {
                    // Maneja errores aquí
                }
            }

            override fun onFailure(call: Call<List<ServicioModel>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })
    }
}
