package com.ambrosio.josue.tutorial.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificacionesViewModel : ViewModel() {
    private val detalleContratoApi = RetrofitClient.detalleContratoAPi
    val listaDetalleContrato = MutableLiveData<List<DetalleContratoModel>>()

    fun obtenerDetalleContratoNotificaciones(proveedorId: Int) {
        detalleContratoApi.obtenerDetalleContratoPorProveedorYEstadoPendiente(proveedorId)
            .enqueue(object : Callback<List<DetalleContratoModel>> {
                override fun onResponse(
                    call: Call<List<DetalleContratoModel>>,
                    response: Response<List<DetalleContratoModel>>
                ) {
                    if (response.isSuccessful) {
                        listaDetalleContrato.postValue(response.body())
                    } else {
                        listaDetalleContrato.postValue(emptyList())
                    }
                }

                override fun onFailure(call: Call<List<DetalleContratoModel>>, t: Throwable) {
                    listaDetalleContrato.postValue(emptyList())
                }
            })
    }
}
