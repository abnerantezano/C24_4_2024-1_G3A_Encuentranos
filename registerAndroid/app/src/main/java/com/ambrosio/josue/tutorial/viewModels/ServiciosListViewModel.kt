package com.ambrosio.josue.tutorial.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.models.ServicioModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiciosListViewModel(
): ViewModel() {

    private val servicioApi = RetrofitClient.servicioApi
    val listaServicios = MutableLiveData<List<ServicioModel>>()

    fun obtenerServicios() {
        servicioApi.listarServicios().enqueue(object : Callback<List<ServicioModel>> {
            override fun onResponse(call: Call<List<ServicioModel>>, response: Response<List<ServicioModel>>) {
                if (response.isSuccessful) {
                    listaServicios.postValue(response.body())
                } else {
                    // Handle errors here
                }
            }

            override fun onFailure(call: Call<List<ServicioModel>>, t: Throwable) {
                // Handle connection errors here
            }
        })
    }

}
