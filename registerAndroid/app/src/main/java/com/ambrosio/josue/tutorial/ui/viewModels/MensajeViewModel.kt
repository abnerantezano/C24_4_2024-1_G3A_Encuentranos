package com.ambrosio.josue.tutorial.ui.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.ChatModel
import com.ambrosio.josue.tutorial.data.models.MensajeModel
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MensajeViewModel : ViewModel() {
    private val mensajeApi = RetrofitClient.mensajeApi
    val listaMensaje = MutableLiveData<List<MensajeModel>>()
    val listaMensajePorId = MutableLiveData<List<MensajeModel>>()
    private var job: Job? = null

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun enviarMensaje(chatId: Int, contenido: String, idEmisor: Int, idReceptor: Int) {
        val nuevoMensaje = MensajeModel(
            idMensaje = 0, // Asignar un valor temporal o generado por el backend
            idEmisor = UsuarioModel(idUsuario = idEmisor),
            idReceptor = UsuarioModel(idUsuario = idReceptor),
            idChat = ChatModel(chatId),
            mensaje = contenido,
            fechaCreacion = obtenerFechaActual()
        )

        mensajeApi.crearMensaje(chatId, nuevoMensaje).enqueue(object : Callback<MensajeModel> {
            override fun onResponse(call: Call<MensajeModel>, response: Response<MensajeModel>) {
                if (response.isSuccessful) {
                    response.body()?.let { mensaje ->
                        val mensajesActuales = listaMensajePorId.value.orEmpty().toMutableList()
                        mensajesActuales.add(mensaje)
                        listaMensajePorId.value = mensajesActuales
                    }
                }
            }

            override fun onFailure(call: Call<MensajeModel>, t: Throwable) {
                // Manejar errores
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaActual(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return currentDateTime.format(formatter)
    }

    private fun obtenerHoraActual(): String {
        val formato = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return formato.format(Date())
    }

    // Método para iniciar el sondeo
    fun iniciarSondeo(idChat: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                obtenerMensajesPorIdChat(idChat)
                delay(5000) // Sondea cada 5 segundos
            }
        }
    }

    // Método para detener el sondeo
    fun detenerSondeo() {
        job?.cancel()
    }
}
