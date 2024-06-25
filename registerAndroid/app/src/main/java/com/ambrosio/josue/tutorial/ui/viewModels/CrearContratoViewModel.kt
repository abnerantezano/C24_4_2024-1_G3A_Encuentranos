package com.ambrosio.josue.tutorial.ui.viewModels

import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.ContratoModel
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearContratoViewModel : ViewModel() {

    private val contratoApi = RetrofitClient.contratoApi
    private val detalleContratoApi = RetrofitClient.detalleContratoAPi

    fun crearContrato(contrato: ContratoModel, onResult: (ContratoModel?) -> Unit) {
        contratoApi.crearContrato(contrato).enqueue(object : Callback<ContratoModel> {
            override fun onResponse(call: Call<ContratoModel>, response: Response<ContratoModel>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<ContratoModel>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun crearDetalleContrato(detalleContrato: DetalleContratoModel, onResult: (DetalleContratoModel?) -> Unit) {
        detalleContratoApi.detalleContratoAgregar(detalleContrato).enqueue(object : Callback<DetalleContratoModel> {
            override fun onResponse(call: Call<DetalleContratoModel>, response: Response<DetalleContratoModel>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<DetalleContratoModel>, t: Throwable) {
                onResult(null)
            }
        })
    }
}
