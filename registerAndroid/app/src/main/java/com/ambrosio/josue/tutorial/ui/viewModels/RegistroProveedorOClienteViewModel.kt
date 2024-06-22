package com.ambrosio.josue.tutorial.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.data.models.ClienteModel
import com.ambrosio.josue.tutorial.data.models.DistritoModel
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.data.servicios.ClienteApi
import com.ambrosio.josue.tutorial.data.servicios.DistritoApi
import com.ambrosio.josue.tutorial.data.servicios.ProveedorApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroProveedorOClienteViewModel(
    private val proveedorApi: ProveedorApi,
    private val distritoApi: DistritoApi,
    private val clienteApi: ClienteApi
) : ViewModel() {

    private val _distritos = MutableLiveData<List<DistritoModel>>()
    val distritos: LiveData<List<DistritoModel>> = _distritos

    private val _registroResult = MutableLiveData<Boolean>()
    val registroResult: LiveData<Boolean> = _registroResult

    private val _idRegistro = MutableLiveData<Int>()
    val idRegistro: LiveData<Int> = _idRegistro


    fun registrar(nuevoRegistro: Any) {
        when (nuevoRegistro) {
            is ProveedorModel -> registrarProveedor(nuevoRegistro)
            is ClienteModel -> registrarCliente(nuevoRegistro)
        }
    }

    private fun registrarProveedor(proveedor: ProveedorModel) {
        proveedorApi.agregarProveedor(proveedor).enqueue(object : Callback<ProveedorModel> {
            override fun onResponse(call: Call<ProveedorModel>, response: Response<ProveedorModel>) {
                if (response.isSuccessful) {
                    val proveedorRegistrado = response.body()
                    proveedorRegistrado?.let {
                        _idRegistro.postValue(it.idProveedor)
                        _registroResult.postValue(true)
                    } ?: run {
                        _registroResult.postValue(false)
                    }
                } else {
                    _registroResult.postValue(false)
                }
            }

            override fun onFailure(call: Call<ProveedorModel>, t: Throwable) {
                _registroResult.postValue(false)
            }
        })
    }

    private fun registrarCliente(cliente: ClienteModel) {
        clienteApi.agregarCliente(cliente).enqueue(object : Callback<ClienteModel> {
            override fun onResponse(call: Call<ClienteModel>, response: Response<ClienteModel>) {
                if (response.isSuccessful) {
                    val clienteRegistrado = response.body()
                    clienteRegistrado?.let {
                        _idRegistro.postValue(it.idCliente)
                        _registroResult.postValue(true)
                    } ?: run {
                        _registroResult.postValue(false)
                    }
                } else {
                    _registroResult.postValue(false)
                }
            }

            override fun onFailure(call: Call<ClienteModel>, t: Throwable) {
                _registroResult.postValue(false)
            }
        })
    }
}
