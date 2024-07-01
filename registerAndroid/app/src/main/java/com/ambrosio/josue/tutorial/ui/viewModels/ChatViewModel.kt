package com.ambrosio.josue.tutorial.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.ChatModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : ViewModel() {
    private val chatApi = RetrofitClient.chatApi
    val listachats = MutableLiveData<List<ChatModel>>()
    val listachatsIdProveedor = MutableLiveData<List<ChatModel>>()
    val listachatsIdCliente = MutableLiveData<List<ChatModel>>()
    val idChat = MutableLiveData<Int>()

    fun crearChat(idCliente: Int, idProveedor: Int) {
        chatApi.obtenerOcrearChat(idCliente, idProveedor).enqueue(object : Callback<ChatModel> {
            override fun onResponse(call: Call<ChatModel>, response: Response<ChatModel>) {
                if (response.isSuccessful) {
                    val chatCreado = response.body()
                    chatCreado?.let {
                        idChat.postValue(it.idChat)
                        Log.d("ChatViewModel", "Chat creado exitosamente: $it")
                    }
                } else {
                    Log.e("ChatViewModel", "Error al crear el chat: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ChatModel>, t: Throwable) {
                Log.e("ChatViewModel", "Fallo al crear el chat", t)
            }
        })
    }

    fun obtenerChats() {
        chatApi.listarChats().enqueue(object : Callback<List<ChatModel>> {
            override fun onResponse(call: Call<List<ChatModel>>, response: Response<List<ChatModel>>) {
                if (response.isSuccessful) {
                    listachats.postValue(response.body())
                    Log.d("MensajeViewModel", "Mensajes loaded successfully: ${response.body()}")
                } else {
                    Log.e("MensajeViewModel", "Error loading mensajes: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<ChatModel>>, t: Throwable) {
                Log.e("MensajeViewModel", "Failed to load mensajes", t)
            }
        })
    }

    fun obtenerChatsIdCliente(idCliente: Int) {
        chatApi.obtenerChatsPorIdCliente(idCliente).enqueue(object : Callback<List<ChatModel>> {
            override fun onResponse(call: Call<List<ChatModel>>, response: Response<List<ChatModel>>) {
                if (response.isSuccessful) {
                    listachatsIdCliente.postValue(response.body())
                    Log.d("MensajeViewModel", "Mensajes loaded successfully: ${response.body()}")
                } else {
                    Log.e("MensajeViewModel", "Error loading mensajes: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<ChatModel>>, t: Throwable) {
                Log.e("MensajeViewModel", "Failed to load mensajes", t)
            }
        })
    }

    fun obtenerChatsIdProveedor(idProveedor: Int) {
        chatApi.obtenerChatsPorIdProveedor(idProveedor).enqueue(object : Callback<List<ChatModel>> {
            override fun onResponse(call: Call<List<ChatModel>>, response: Response<List<ChatModel>>) {
                if (response.isSuccessful) {
                    listachatsIdProveedor.postValue(response.body())
                    Log.d("MensajeViewModel", "Mensajes loaded successfully: ${response.body()}")
                } else {
                    Log.e("MensajeViewModel", "Error loading mensajes: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<ChatModel>>, t: Throwable) {
                Log.e("MensajeViewModel", "Failed to load mensajes", t)
            }
        })
    }
}
