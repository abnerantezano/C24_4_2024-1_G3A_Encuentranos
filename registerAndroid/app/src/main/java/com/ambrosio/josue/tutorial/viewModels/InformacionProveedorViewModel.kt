package com.ambrosio.josue.tutorial.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.models.ProveedorModel
import com.ambrosio.josue.tutorial.models.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class InformacionProveedorViewModel(): ViewModel() {

    private val _proveedor = MutableLiveData<ProveedorModel>()
    val proveedor: LiveData<ProveedorModel> get() = _proveedor


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun ejecutarConAutenticacion(accion: (String, String?) -> Unit) {
        val usuarioActual = auth.currentUser
        if (usuarioActual != null) {
            usuarioActual.getIdToken(true).addOnCompleteListener { tarea ->
                if (tarea.isSuccessful) {
                    val token = tarea.result?.token
                    accion(usuarioActual.email!!, token)
                } else {
                }
            }
        } else {
        }
    }
    fun obtenerDatosProveedorPorId(idProveedor: Int) {
        ejecutarConAutenticacion { _, token ->
            val cliente = OkHttpClient()
            val url = "${InicioViewModel.BASE_URL}/proveedor/buscar/$idProveedor"
            val solicitud = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $token")
                .build()

            cliente.newCall(solicitud).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("InicioViewModel", "Error en la llamada: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) {
                            return
                        }

                        val responseBody = response.body?.string()
                        try {
                            // Parsear el objeto UsuarioModel
                            val jsonObject = JSONObject(responseBody)

                            val usuarioJson = jsonObject.getJSONObject("idUsuario")
                            val usuarioModel = UsuarioModel(
                                idUsuario = usuarioJson.optInt("idUsuario", -1),
                                correo = usuarioJson.optString("correo", ""),
                                contrasena = usuarioJson.optString("contrasena", ""),
                                imageUrl = usuarioJson.optString("imageUrl", null),
                                activo = usuarioJson.optBoolean("activo", true),
                                fechaCreacion = usuarioJson.optString("fechaCreacion", "")
                            )

                            // Parsear el objeto ProveedorModel
                            val proveedorModel = ProveedorModel(
                                idProveedor = jsonObject.optInt("idProveedor", -1),
                                idUsuario = usuarioModel,  // Asignar el objeto UsuarioModel
                                nombre = jsonObject.optString("nombre", ""),
                                apellidoPaterno = jsonObject.optString("apellidoPaterno", ""),
                                apellidoMaterno = jsonObject.optString("apellidoMaterno", ""),
                                celular = jsonObject.optString("celular", ""),
                                descripcion = jsonObject.optString("descripcion", ""),
                            )
                            _proveedor.postValue(proveedorModel)
                        } catch (e: JSONException) {
                            Log.e("InicioViewModel", "Error al parsear JSON: ${e.message}")
                        }
                    }
                }
            })
        }
    }
}