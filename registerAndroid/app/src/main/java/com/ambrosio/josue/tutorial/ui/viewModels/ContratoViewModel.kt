package com.ambrosio.josue.tutorial.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import com.ambrosio.josue.tutorial.data.models.ServicioProveedorModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContratoViewModel(
    var context: Context
): ViewModel() {
    val detalleContratoAPi = RetrofitClient.detalleContratoAPi
    private lateinit var detalleContratoModel: DetalleContratoModel
    val listaDetalleContratoModel = MutableLiveData<List<DetalleContratoModel>>()
    val obtenerDetalleContratoPorIdProveedor = MutableLiveData<List<DetalleContratoModel>?>()

    fun obtenerDetalleContratos() {
        detalleContratoAPi.listarDetallesContratos().enqueue(object : Callback<List<DetalleContratoModel>> {
            override fun onResponse(call: Call<List<DetalleContratoModel>>, response: Response<List<DetalleContratoModel>>) {
                if (response.isSuccessful) {
                    listaDetalleContratoModel.postValue(response.body())
                } else {
                    // Maneja errores aquí
                }
            }

            override fun onFailure(call: Call<List<DetalleContratoModel>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })
    }

    fun obtenerDetalleContratoPorIdProveedor(idProveedor: Int) {
        detalleContratoAPi.obtenerDetalleContratoPorIdProveedor(idProveedor)
            .enqueue(object : Callback<List<DetalleContratoModel>> {
                override fun onResponse(
                    call: Call<List<DetalleContratoModel>>,
                    response: Response<List<DetalleContratoModel>>
                ) {
                    if (response.isSuccessful) {
                        val detalleContratos = response.body()
                        Log.d("MiContratoActivity", "Contratos obtenidos: $detalleContratos")
                        obtenerDetalleContratoPorIdProveedor.postValue(detalleContratos)
                    } else {
                        Log.e("MiServicioActivity", "Error en la respuesta: ${response.code()}")
                        obtenerDetalleContratoPorIdProveedor.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<DetalleContratoModel>>, t: Throwable) {
                    Log.e("MiServicioActivity", "Error al obtener servicios: ${t.message}")
                    obtenerDetalleContratoPorIdProveedor.postValue(null)
                }
            })
    }
}
