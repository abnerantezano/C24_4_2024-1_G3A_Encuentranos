package com.ambrosio.josue.tutorial.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.models.ServicioProveedorModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicioProveedorViewModel(
    var context: Context
): ViewModel() {
    val servicioProveedorApi = RetrofitClient.servicioProveedorApi
    private lateinit var servicioProveedorModel: ServicioProveedorModel
    val listaServiciosProveedores = MutableLiveData<List<ServicioProveedorModel>>()

    fun obtenerServiciosProveedor() {
        servicioProveedorApi.listarServiciosProveedores().enqueue(object : Callback<List<ServicioProveedorModel>> {
            override fun onResponse(call: Call<List<ServicioProveedorModel>>, response: Response<List<ServicioProveedorModel>>) {
                if (response.isSuccessful) {
                    listaServiciosProveedores.postValue(response.body())
                } else {
                    // Maneja errores aquí
                }
            }

            override fun onFailure(call: Call<List<ServicioProveedorModel>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })
    }
}