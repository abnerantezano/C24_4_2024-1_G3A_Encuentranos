package com.ambrosio.josue.tutorial.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.ChatModel
import com.ambrosio.josue.tutorial.data.models.MensajeModel
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MensajeViewModel : ViewModel() {
    private val mensajeApi = RetrofitClient.mensajeApi
    val listaMensaje = MutableLiveData<List<MensajeModel>>()
    val listaMensajePorId = MutableLiveData<List<MensajeModel>>()

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

    fun obtenerMensajesPorIdChat(idChat: Int) {
        mensajeApi.obtenerMensajesPorIdChat(idChat).enqueue(object : Callback<List<MensajeModel>> {
            override fun onResponse(call: Call<List<MensajeModel>>, response: Response<List<MensajeModel>>) {
                if (response.isSuccessful) {
                    listaMensajePorId.postValue(response.body())
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

    fun enviarMensaje(idChat: Int, contenido: String, emisor: UsuarioModel, receptor: UsuarioModel) {
        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val horaActual = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        val mensaje = MensajeModel(
            idMensaje = 0, // Assuming the backend generates the ID
            idEmisor = emisor,
            idReceptor = receptor,
            idChat = ChatModel(idChat),
            mensaje = contenido,
            fechaEnvio = fechaActual,
            horaEnvio = horaActual
        )

        mensajeApi.crearMensaje(mensaje).enqueue(object : Callback<MensajeModel> {
            override fun onResponse(call: Call<MensajeModel>, response: Response<MensajeModel>) {
                if (response.isSuccessful) {
                    Log.d("MensajeViewModel", "Mensaje enviado: ${response.body()}")
                } else {
                    Log.e("MensajeViewModel", "Error al enviar mensaje: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MensajeModel>, t: Throwable) {
                Log.e("MensajeViewModel", "Falló al enviar mensaje", t)
            }
        })
    }
}
