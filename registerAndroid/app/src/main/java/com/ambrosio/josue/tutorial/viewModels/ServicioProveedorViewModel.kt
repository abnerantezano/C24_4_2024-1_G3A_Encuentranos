package com.ambrosio.josue.tutorial.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.models.ServicioProveedorModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicioProveedorViewModel : ViewModel() {
    private val servicioProveedorApi = RetrofitClient.servicioProveedorApi
    val listaServiciosProveedores = MutableLiveData<List<ServicioProveedorModel>>()
    val obtenerServicioProveedorPorIdProveedor = MutableLiveData<List<ServicioProveedorModel>?>()

    fun obtenerServiciosProveedor() {
        servicioProveedorApi.listarServiciosProveedores().enqueue(object : Callback<List<ServicioProveedorModel>> {
            override fun onResponse(call: Call<List<ServicioProveedorModel>>, response: Response<List<ServicioProveedorModel>>) {
                if (response.isSuccessful) {
                    listaServiciosProveedores.postValue(response.body())
                } else {
                    // Maneja errores aquí
                }
            }

            override fun onFailure(call: Call<List<ServicioProveedorModel>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })
    }

    fun agregarServicioProveedor(serviciosProveedor: List<ServicioProveedorModel>, callback: (Boolean) -> Unit) {
        servicioProveedorApi.agregarServicioProveedor(serviciosProveedor).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun obtenerServicioProveedorPorIdProveedor(idProveedor: Int) {
        servicioProveedorApi.obtenerServicioProveedorPorIdProveedor(idProveedor)
            .enqueue(object : Callback<List<ServicioProveedorModel>> {
                override fun onResponse(
                    call: Call<List<ServicioProveedorModel>>,
                    response: Response<List<ServicioProveedorModel>>
                ) {
                    if (response.isSuccessful) {
                        val servicios = response.body()
                        Log.d("MiServicioActivity", "Servicios obtenidos: $servicios")
                        obtenerServicioProveedorPorIdProveedor.postValue(servicios)
                    } else {
                        Log.e("MiServicioActivity", "Error en la respuesta: ${response.code()}")
                        obtenerServicioProveedorPorIdProveedor.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<ServicioProveedorModel>>, t: Throwable) {
                    Log.e("MiServicioActivity", "Error al obtener servicios: ${t.message}")
                    obtenerServicioProveedorPorIdProveedor.postValue(null)
                }
            })
    }

}
