package com.ambrosio.josue.tutorial.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.DetalleCalificacionModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleCalificacionViewModel : ViewModel() {
    private val detalleCalificacionApi = RetrofitClient.detalleCalificacionApi
    val listarDetalleCalificacionPorIdProveedorYIdServicio = MutableLiveData<List<DetalleCalificacionModel>?>()

    fun listarResenasDeProveedor(idProveedor: Int) {
        detalleCalificacionApi.detalleCalificacionPorIdProveedor(idProveedor)
            .enqueue(object : Callback<List<DetalleCalificacionModel>> {
                override fun onResponse(
                    call: Call<List<DetalleCalificacionModel>>,
                    response: Response<List<DetalleCalificacionModel>>
                ) {
                    if (response.isSuccessful) {
                        val servicios = response.body()
                        listarDetalleCalificacionPorIdProveedorYIdServicio.postValue(servicios)
                    } else {
                        listarDetalleCalificacionPorIdProveedorYIdServicio.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<DetalleCalificacionModel>>, t: Throwable) {
                    listarDetalleCalificacionPorIdProveedorYIdServicio.postValue(null)
                }
            })
    }
}
