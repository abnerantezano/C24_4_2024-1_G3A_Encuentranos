package com.ambrosio.josue.tutorial.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.models.ClienteModel
import com.ambrosio.josue.tutorial.models.DistritoModel
import com.ambrosio.josue.tutorial.models.ProveedorModel
import com.ambrosio.josue.tutorial.models.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class InicioViewModel : ViewModel() {

    // LiveData para datos que se utlizaran en los UI's

    private val _idProveedor = MutableLiveData<Int>()
    val idProveedor: LiveData<Int> get() = _idProveedor

    private val _nombreUsuario = MutableLiveData<String>()
    val nombreUsuario: LiveData<String> get() = _nombreUsuario

    private val _apellidoPaternoUsuario = MutableLiveData<String>()
    val apellidoPaternoUsuario: LiveData<String> get() = _apellidoPaternoUsuario

    private val _apellidoMaternoUsuario = MutableLiveData<String>()
    val apellidoMaternoUsuario: LiveData<String> get() = _apellidoMaternoUsuario

    private val _fechaNacimiento = MutableLiveData<String>()
    val fechaNacimiento: LiveData<String> get() = _fechaNacimiento

    private val _dniUsuario = MutableLiveData<String>()
    val dniUsuario: LiveData<String> get() = _dniUsuario

    private val _celularUsuario = MutableLiveData<String>()
    val celularUsuario: LiveData<String> get() = _celularUsuario

    private val _distritoUsuario = MutableLiveData<String>()
    val distritoUsuario: LiveData<String> get() = _distritoUsuario

    private val _sexoUsuario = MutableLiveData<String>()
    val sexoUsuario: LiveData<String> get() = _sexoUsuario

    private val _descripcionUsuario = MutableLiveData<String>()
    val descripcionUsuario: LiveData<String> get() = _descripcionUsuario



    private val _contrasenaUsuario = MutableLiveData<String>()
    val contrasenaUsuario: LiveData<String> get() = _contrasenaUsuario

    private val _mensajeError = MutableLiveData<String>()
    val mensajeError: LiveData<String> get() = _mensajeError

    private val _proveedor = MutableLiveData<ProveedorModel>()
    val proveedor: LiveData<ProveedorModel> get() = _proveedor


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val cliente = OkHttpClient()

    companion object {
        const val BASE_URL = "http://192.168.100.13:4000"
    }


    // Método para verificar la autenticación del usuario actual
    fun verificarAutenticacionUsuario() {
        val usuarioActual = auth.currentUser
        if (usuarioActual != null) {
            usuarioActual.getIdToken(true).addOnCompleteListener { tarea ->
                if (tarea.isSuccessful) {
                    val token = tarea.result?.token
                    Log.d("InicioViewModel", "Token obtenido: $token")
                    obtenerNombreUsuario(usuarioActual.email, token)
                } else {
                    manejarErrorAutenticacion("Error al obtener token de usuario", tarea.exception)
                }
            }
        } else {
            _mensajeError.postValue("Usuario no autenticado")
            Log.e("InicioViewModel", "Usuario no autenticado")
        }
    }


    // Método para obtener el ID del proveedor
    fun obtenerIdProveedor() {
        ejecutarConAutenticacion { email, token ->
            obtenerIdProveedorPorEmail(email, token)
        }
    }


    // Método privado para ejecutar acciones autenticadas
    private fun ejecutarConAutenticacion(accion: (String, String?) -> Unit) {
        val usuarioActual = auth.currentUser
        if (usuarioActual != null) {
            usuarioActual.getIdToken(true).addOnCompleteListener { tarea ->
                if (tarea.isSuccessful) {
                    val token = tarea.result?.token
                    accion(usuarioActual.email!!, token)
                } else {
                    manejarErrorAutenticacion("Error al obtener token de usuario", tarea.exception)
                }
            }
        } else {
            _mensajeError.postValue("Usuario no autenticado")
        }
    }

    // Método para manejar errores de autenticación
    private fun manejarErrorAutenticacion(mensajeError: String, excepcion: Exception?) {
        Log.e("InicioViewModel", mensajeError, excepcion)
        _mensajeError.postValue(mensajeError)
    }

    // Método para obtener el ID de usuario por email
    private fun obtenerIdProveedorPorEmail(email: String, token: String?) {
        val cliente = OkHttpClient()
        val solicitud = Request.Builder()
            .url("$BASE_URL/usuario/verificar/$email")
            .addHeader("Authorization", "Bearer $token")
            .build()

        cliente.newCall(solicitud).enqueue(crearCallback { respuesta ->
            if (respuesta != null) {
                val jsonObject = JSONObject(respuesta)
                val idUsuario = jsonObject.optInt("idUsuario", -1)
                if (idUsuario != -1) {
                    obtenerIdProveedorPorIdUsuario(idUsuario, token)
                } else {
                    _mensajeError.postValue("Error: Usuario no encontrado")
                }
            }
        })
    }

    // Método para obtener el ID de proveedor por ID de usuario
    private fun obtenerIdProveedorPorIdUsuario(idUsuario: Int, token: String?) {
        val cliente = OkHttpClient()
        val solicitud = Request.Builder()
            .url("$BASE_URL/proveedor/buscar-usuario/$idUsuario")
            .addHeader("Authorization", "Bearer $token")
            .build()

        cliente.newCall(solicitud).enqueue(crearCallback { respuesta ->
            if (respuesta != null) {
                val jsonObject = JSONObject(respuesta)
                val idProveedor = jsonObject.optInt("idProveedor", -1)
                if (idProveedor != -1) {
                    _idProveedor.postValue(idProveedor)
                } else {
                    _mensajeError.postValue("Error: Proveedor no encontrado")
                }
            }
        })
    }

    // Método para obtener el nombre de usuario
    fun obtenerNombreUsuario(email: String?, token: String?) {
        if (email == null || token == null) return

        val solicitud = Request.Builder()
            .url("$BASE_URL/usuario/verificar/$email")
            .addHeader("Authorization", "Bearer $token")
            .build()

        cliente.newCall(solicitud).enqueue(crearCallback { respuesta ->
            if (respuesta != null) {
                val jsonObject = JSONObject(respuesta)
                val idUsuario = jsonObject.optInt("idUsuario", -1)
                if (idUsuario != -1) {
                    obtenerDatosUsuario(idUsuario, token)
                } else {
                    _mensajeError.postValue("Error: Usuario no encontrado")
                }
            }
        })
    }

    private fun obtenerDatosUsuario(idUsuario: Int, token: String?) {
        obtenerDatosDesdeEndpoint("$BASE_URL/proveedor/buscar-usuario/$idUsuario", idUsuario, token) {
            if (it == null) {
                obtenerDatosDesdeEndpoint("$BASE_URL/cliente/buscar-usuario/$idUsuario", idUsuario, token) { respuestaCliente ->
                    if (respuestaCliente == null) {
                        _mensajeError.postValue("Error: Nombre no encontrado en cliente ni proveedor")
                    } else {
                        actualizarDatosUsuario(respuestaCliente)
                    }
                }
            } else {
                actualizarDatosUsuario(it)
            }
        }
    }

    private fun obtenerDatosDesdeEndpoint(url: String, idUsuario: Int, token: String?, callback: (String?) -> Unit) {
        val solicitud = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .build()

        cliente.newCall(solicitud).enqueue(crearCallback(callback))
    }

    private fun actualizarDatosUsuario(respuesta: String) {
        val jsonObject = JSONObject(respuesta)
        _nombreUsuario.postValue(jsonObject.optString("nombre", ""))
        _apellidoPaternoUsuario.postValue(jsonObject.optString("apellidoPaterno", ""))
        _apellidoMaternoUsuario.postValue(jsonObject.optString("apellidoMaterno", ""))
        _fechaNacimiento.postValue(jsonObject.optString("fechaNacimiento", ""))
        _dniUsuario.postValue(jsonObject.optString("dni", ""))
        _celularUsuario.postValue(jsonObject.optString("celular", ""))
        _descripcionUsuario.postValue(jsonObject.optString("descripcion", ""))
        _distritoUsuario.postValue(jsonObject.optString("distrito", ""))
        _sexoUsuario.postValue(jsonObject.optString("sexo", ""))
    }

    // Método para manejar errores de red
    private fun manejarErrorRed(excepcion: IOException) {
        Log.e("InicioViewModel", "Error de red: ${excepcion.message}")
        _mensajeError.postValue("Error de red")
    }

    // Método para crear un callback genérico
    private fun crearCallback(accion: (String?) -> Unit): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("InicioViewModel", "Error en la llamada: ${e.message}")
                manejarErrorRed(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val respuesta = response.body?.string()
                    Log.d("InicioViewModel", "Respuesta exitosa: $respuesta")
                    accion(respuesta)
                } else {
                    Log.e("InicioViewModel", "Error en la respuesta: ${response.code}")
                    _mensajeError.postValue("Error al obtener detalles del usuario")
                }
            }
        }
    }

    // Método para obtener la contraseña del usuario
    fun obtenerContrasenaUsuario() {
        ejecutarConAutenticacion { email, token ->
            obtenerContrasenaUsuario(email, token)
        }
    }

    // Método privado para obtener la contraseña del usuario
    private fun obtenerContrasenaUsuario(email: String?, token: String?) {
        if (email == null || token == null) return

        val cliente = OkHttpClient()
        val solicitud = Request.Builder()
            .url("$BASE_URL/usuario/verificar/$email")
            .addHeader("Authorization", "Bearer $token")
            .build()

        cliente.newCall(solicitud).enqueue(crearCallback { respuesta ->
            if (respuesta != null) {
                val jsonObject = JSONObject(respuesta)
                val contrasena = jsonObject.optString("contrasena", "")
                if (contrasena.isNotEmpty()) {
                    _contrasenaUsuario.postValue(contrasena)
                } else {
                    _mensajeError.postValue("Error: Contraseña no encontrada")
                }
            }
        })
    }

}