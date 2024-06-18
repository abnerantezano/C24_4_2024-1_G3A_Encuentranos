package com.ambrosio.josue.tutorial.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.models.DistritoModel
import com.ambrosio.josue.tutorial.models.ProveedorModel
import com.ambrosio.josue.tutorial.servicios.DistritoApi
import com.ambrosio.josue.tutorial.servicios.ProveedorApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroProveedorViewModel(private val proveedorApi: ProveedorApi, private val distritoApi: DistritoApi) : ViewModel() {

    private val _distritos = MutableLiveData<List<DistritoModel>>()
    val distritos: LiveData<List<DistritoModel>> = _distritos

    private val _registroProveedorResult = MutableLiveData<Boolean>()
    val registroProveedorResult: LiveData<Boolean> = _registroProveedorResult

    // LiveData para guardar el id del proveedor
    private val _idProveedor = MutableLiveData<Int>()
    val idProveedor: LiveData<Int> = _idProveedor

    fun listarDistritos() {
        distritoApi.listarDistritos().enqueue(object : Callback<List<DistritoModel>> {
            override fun onResponse(call: Call<List<DistritoModel>>, response: Response<List<DistritoModel>>) {
                if (response.isSuccessful) {
                    _distritos.postValue(response.body())
                } else {
                    _distritos.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DistritoModel>>, t: Throwable) {
                _distritos.postValue(emptyList())
            }
        })
    }

    fun registrarProveedor(proveedor: ProveedorModel) {
        proveedorApi.agregarProveedor(proveedor).enqueue(object : Callback<ProveedorModel> {
            override fun onResponse(call: Call<ProveedorModel>, response: Response<ProveedorModel>) {
                if (response.isSuccessful) {
                    val proveedorRegistrado = response.body()
                    proveedorRegistrado?.let {
                        _idProveedor.postValue(it.idProveedor)
                        _registroProveedorResult.postValue(true)
                    } ?: run {
                        _registroProveedorResult.postValue(false)
                    }
                } else {
                    _registroProveedorResult.postValue(false)
                }
            }

            override fun onFailure(call: Call<ProveedorModel>, t: Throwable) {
                _registroProveedorResult.postValue(false)
            }
        })
    }
}
