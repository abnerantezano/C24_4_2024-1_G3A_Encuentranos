package com.ambrosio.josue.tutorial.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.MensajeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MensajeViewModel : ViewModel() {
    private val mensajeApi = RetrofitClient.mensajeApi
    val listaMensaje = MutableLiveData<List<MensajeModel>>()

    fun obtenerMensajes() {
        mensajeApi.listarMensajes().enqueue(object : Callback<List<MensajeModel>> {
            override fun onResponse(call: Call<List<MensajeModel>>, response: Response<List<MensajeModel>>) {
                if (response.isSuccessful) {
                    listaMensaje.postValue(response.body())
                    Log.d("MensajeViewModel", "Mensajes loaded successfully: ${response.body()}")
                } else {
                    Log.e("MensajeViewModel", "Error loading mensajes: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<MensajeModel>>, t: Throwable) {
                Log.e("MensajeViewModel", "Failed to load mensajes", t)
            }
        })
    }
}
