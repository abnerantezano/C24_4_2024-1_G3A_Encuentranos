package com.ambrosio.josue.tutorial.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModel
import com.ambrosio.josue.tutorial.data.models.NotificacionModel
import com.ambrosio.josue.tutorial.data.servicios.NotificacionApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificacionViewModel() : ViewModel() {

    private val detalleContratoApi = RetrofitClient.detalleContratoAPi
    private val notificacionApi = RetrofitClient.notificacionApi

    private val _notificaciones = MutableLiveData<List<NotificacionModel>>()
    val notificaciones: LiveData<List<NotificacionModel>>
        get() = _notificaciones


    private val _detallesContrato = MutableLiveData<List<DetalleContratoModel>>()
    val detallesContrato: LiveData<List<DetalleContratoModel>> get() = _detallesContrato


    private val _mensajeError = MutableLiveData<String>()
    val mensajeError: LiveData<String>
        get() = _mensajeError

    fun obtenerDetalleContratoNotificaciones(proveedorId: Int) {
        detalleContratoApi.obtenerDetalleContratoPorProveedorYEstadoPendiente(proveedorId)
            .enqueue(object : Callback<List<DetalleContratoModel>> {
                override fun onResponse(
                    call: Call<List<DetalleContratoModel>>,
                    response: Response<List<DetalleContratoModel>>
                ) {
                    if (response.isSuccessful) {
                        _detallesContrato.postValue(response.body())
                    } else {
                        _detallesContrato.postValue(emptyList())
                    }
                }

                override fun onFailure(call: Call<List<DetalleContratoModel>>, t: Throwable) {
                    _detallesContrato.postValue(emptyList())
                }
            })
    }


    fun agregarNotificacion(idCliente: Int, idProveedor: Int, idContrato: Int, notificacion: NotificacionModel) {
        notificacionApi.agregarNotificacion(idCliente, idProveedor, idContrato, notificacion)
            .enqueue(object : Callback<NotificacionModel> {
                override fun onResponse(call: Call<NotificacionModel>, response: Response<NotificacionModel>) {
                    if (response.isSuccessful) {
                        // Manejar la respuesta exitosa si es necesario
                    } else {
                        _mensajeError.postValue("Error al agregar la notificación: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<NotificacionModel>, t: Throwable) {
                    _mensajeError.postValue("Error de red: ${t.message}")
                }
            })
    }
    fun listarNotificaciones() {
        notificacionApi.listarNotificaciones().enqueue(object : Callback<List<NotificacionModel>> {
            override fun onResponse(call: Call<List<NotificacionModel>>, response: Response<List<NotificacionModel>>) {
                if (response.isSuccessful) {
                    _notificaciones.postValue(response.body())
                } else {
                    _mensajeError.postValue("Error al obtener las notificaciones: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<NotificacionModel>>, t: Throwable) {
                _mensajeError.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun actualizarEstadoNotificacion(id: Int) {
        notificacionApi.actualizarEstadoNotificacion(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    _mensajeError.postValue("Error al actualizar el estado de la notificación: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _mensajeError.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun eliminarNotificacion(id: Int) {
        notificacionApi.eliminarNotificacion(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    _mensajeError.postValue("Error al eliminar la notificación: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _mensajeError.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun listarNotificacionesPorIdCliente(idCliente: Int) {
        notificacionApi.listarNotificacionesPorIdCliente(idCliente).enqueue(object : Callback<List<NotificacionModel>> {
            override fun onResponse(call: Call<List<NotificacionModel>>, response: Response<List<NotificacionModel>>) {
                if (response.isSuccessful) {
                    _notificaciones.postValue(response.body())
                } else {
                    _mensajeError.postValue("Error al obtener las notificaciones por ID de cliente: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<NotificacionModel>>, t: Throwable) {
                _mensajeError.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun listarNotificacionesPorIdProveedor(idProveedor: Int) {
        notificacionApi.listarNotificacionesPorIdProveedor(idProveedor).enqueue(object : Callback<List<NotificacionModel>> {
            override fun onResponse(call: Call<List<NotificacionModel>>, response: Response<List<NotificacionModel>>) {
                if (response.isSuccessful) {
                    _notificaciones.postValue(response.body())
                } else {
                    _mensajeError.postValue("Error al obtener las notificaciones por ID de proveedor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<NotificacionModel>>, t: Throwable) {
                _mensajeError.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun listarNotificacionesPorEstado(estado: String) {
        notificacionApi.listarNotificacionesPorEstado(estado).enqueue(object : Callback<List<NotificacionModel>> {
            override fun onResponse(call: Call<List<NotificacionModel>>, response: Response<List<NotificacionModel>>) {
                if (response.isSuccessful) {
                    _notificaciones.postValue(response.body())
                } else {
                    _mensajeError.postValue("Error al obtener las notificaciones por estado: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<NotificacionModel>>, t: Throwable) {
                _mensajeError.postValue("Error de red: ${t.message}")
            }
        })
    }

}
