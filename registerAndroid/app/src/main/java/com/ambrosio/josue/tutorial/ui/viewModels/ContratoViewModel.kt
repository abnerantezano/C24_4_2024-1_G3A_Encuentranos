package com.ambrosio.josue.tutorial.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.ContratoModel
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContratoViewModel : ViewModel() {
    private val contratoApi = RetrofitClient.contratoApi
    private val detalleContratoApi = RetrofitClient.detalleContratoAPi

    val listaContratos = MutableLiveData<List<ContratoModel>>()
    val listaDetalleContratos = MutableLiveData<List<DetalleContratoModel>>()
    val detalleContratoPorIdProveedor = MutableLiveData<List<DetalleContratoModel>?>()

    fun obtenerDetalleContratos() {
        contratoApi.listarContratos().enqueue(object : Callback<List<ContratoModel>> {
            override fun onResponse(call: Call<List<ContratoModel>>, response: Response<List<ContratoModel>>) {
                if (response.isSuccessful) {
                    listaContratos.postValue(response.body())
                } else {
                    Log.e("ContratoViewModel", "Error al obtener contratos: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ContratoModel>>, t: Throwable) {
                Log.e("ContratoViewModel", "Error de conexión al obtener contratos: ${t.message}")
            }
        })
    }

    fun aceptarContratoProveedor(idContrato: Int) {
        contratoApi.aceptarContratoProveedor(idContrato).enqueue(object : Callback<ContratoModel> {
            override fun onResponse(call: Call<ContratoModel>, response: Response<ContratoModel>) {
                if (response.isSuccessful) {
                    Log.d("ContratoViewModel", "Contrato aceptado exitosamente")
                } else {
                    Log.e("ContratoViewModel", "Error al aceptar contrato: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ContratoModel>, t: Throwable) {
                Log.e("ContratoViewModel", "Error de conexión al aceptar contrato: ${t.message}")
            }
        })
    }

    fun denegarContratoProveedor(idContrato: Int) {
        contratoApi.denegarContratoProveedor(idContrato).enqueue(object : Callback<ContratoModel> {
            override fun onResponse(call: Call<ContratoModel>, response: Response<ContratoModel>) {
                if (response.isSuccessful) {
                    Log.d("ContratoViewModel", "Contrato denegado exitosamente")
                } else {
                    Log.e("ContratoViewModel", "Error al denegar contrato: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ContratoModel>, t: Throwable) {
                Log.e("ContratoViewModel", "Error de conexión al denegar contrato: ${t.message}")
            }
        })
    }

    fun obtenerDetalleContratoPorProveedorYEstadoAceptado(idProveedor: Int) {
        detalleContratoApi.obtenerDetalleContratoPorProveedorYEstadoAceptado(idProveedor)
            .enqueue(object : Callback<List<DetalleContratoModel>> {
                override fun onResponse(
                    call: Call<List<DetalleContratoModel>>,
                    response: Response<List<DetalleContratoModel>>
                ) {
                    if (response.isSuccessful) {
                        val detalleContratos = response.body()
                        Log.d("MiContratoActivity", "Contratos obtenidos: $detalleContratos")
                        detalleContratoPorIdProveedor.postValue(detalleContratos)
                    } else {
                        Log.e("MiServicioActivity", "Error en la respuesta: ${response.code()}")
                        detalleContratoPorIdProveedor.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<DetalleContratoModel>>, t: Throwable) {
                    Log.e("MiServicioActivity", "Error al obtener servicios: ${t.message}")
                    detalleContratoPorIdProveedor.postValue(null)
                }
            })
    }
}
