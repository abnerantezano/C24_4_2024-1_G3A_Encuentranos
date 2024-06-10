package com.ambrosio.josue.tutorial.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.models.ServicioModel
import com.proyecto.encuentranos.models.DetalleContratoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContratoViewModel(
    var context: Context
): ViewModel() {
    val apiService = RetrofitClient.apiService
    private lateinit var detalleContratoModel: DetalleContratoModel
    val listaDetalleContratoModel = MutableLiveData<List<DetalleContratoModel>>()
    fun obtenerDetalleContratos() {
        apiService.listarDetallesContratos().enqueue(object : Callback<List<DetalleContratoModel>> {
            override fun onResponse(call: Call<List<DetalleContratoModel>>, response: Response<List<DetalleContratoModel>>) {
                if (response.isSuccessful) {
                    listaDetalleContratoModel.postValue(response.body())
                } else {
                    // Maneja errores aquí
                }
            }

            override fun onFailure(call: Call<List<DetalleContratoModel>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })
    }

}