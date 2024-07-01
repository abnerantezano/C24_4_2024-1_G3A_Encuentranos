package com.ambrosio.josue.tutorial.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.data.servicios.ProveedorApi
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformacionProveedorViewModel : ViewModel() {

    private val _proveedor = MutableLiveData<ProveedorModel>()
    val proveedor: LiveData<ProveedorModel> get() = _proveedor

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val proveedorApi: ProveedorApi = RetrofitClient.proveedorApi

    private val _idUsuarioProveedor = MutableLiveData<Int>()
    val idUsuarioProveedor: LiveData<Int> get() = _idUsuarioProveedor



    private fun ejecutarConAutenticacion(accion: (String, String?) -> Unit) {
        val usuarioActual = auth.currentUser
        if (usuarioActual != null) {
            usuarioActual.getIdToken(true).addOnCompleteListener { tarea ->
                if (tarea.isSuccessful) {
                    val token = tarea.result?.token
                    accion(usuarioActual.email!!, token)
                } else {
                    Log.e("InformacionProveedorVM", "Error obteniendo token")
                }
            }
        } else {
            Log.e("InformacionProveedorVM", "Usuario no autenticado")
        }
    }

    fun obtenerDatosProveedorPorId(idUsuarioProveedor: Int) {
        ejecutarConAutenticacion { _, token ->
            val call = proveedorApi.obtenerDatosDeUsuarioPorId(idUsuarioProveedor)
            call.enqueue(object : Callback<ProveedorModel> {
                override fun onResponse(call: Call<ProveedorModel>, response: Response<ProveedorModel>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _proveedor.postValue(it)
                        }
                    } else {
                        Log.e("InformacionProveedorVM", "Error en la respuesta: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ProveedorModel>, t: Throwable) {
                    Log.e("InformacionProveedorVM", "Error en la llamada: ${t.message}")
                }
            })
        }
    }

    fun obtenerIdUsuarioProveedor(idProveedor: Int) {
        ejecutarConAutenticacion { _, token ->
            val call = proveedorApi.obtenerDatosDeUsuarioPorId(idProveedor)
            call.enqueue(object : Callback<ProveedorModel> {
                override fun onResponse(call: Call<ProveedorModel>, response: Response<ProveedorModel>) {
                    if (response.isSuccessful) {
                        val proveedor = response.body()
                        proveedor?.let {
                            val idUsuarioProveedor = it.idUsuario?.idUsuario ?: -1 // Si idUsuario es nullable, asigna un valor por defecto (en este caso, -1)
                            _idUsuarioProveedor.postValue(idUsuarioProveedor)
                            _proveedor.postValue(it)
                        }
                    } else {
                        Log.e("InformacionProveedorVM", "Error en la respuesta: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ProveedorModel>, t: Throwable) {
                    Log.e("InformacionProveedorVM", "Error en la llamada: ${t.message}")
                }
            })
        }
    }
}
