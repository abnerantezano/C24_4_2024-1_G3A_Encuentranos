package com.ambrosio.josue.tutorial.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.ChatModel
import com.ambrosio.josue.tutorial.data.models.MensajeModel

class ChatViewModel : ViewModel(){
    private val chatApi = RetrofitClient.chatApi
    val listachats = MutableLiveData<List<ChatModel>>()

}