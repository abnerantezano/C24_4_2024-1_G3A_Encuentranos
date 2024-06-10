package com.ambrosio.josue.tutorial.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.ApiService
import com.ambrosio.josue.tutorial.models.TipoUsuarioModel
import com.ambrosio.josue.tutorial.models.UsuarioModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioViewModel(
    private val apiService: ApiService
) : ViewModel() {

    fun crearUsuario(
        email: String,
        contrasena: String,
        tipoUsuarioId: Int,
        onSuccess: (UsuarioModel) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val tipoUsuario = TipoUsuarioModel(tipoUsuarioId, "")
        val nuevoUsuario = UsuarioModel(
            idUsuario = 0,
            idTipo = tipoUsuario,
            correo = email,
            contrasena = contrasena,
            imageUrl = null,
            activo = true,
            fechaCreacion = "2024-06-03"
        )

        apiService.agregarUsuario(nuevoUsuario).enqueue(object : Callback<UsuarioModel> {
            override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                if (response.isSuccessful) {
                    val usuarioAgregado = response.body()
                    onSuccess(usuarioAgregado!!)
                } else {
                    onFailure("Error al crear el usuario")
                }
            }

            override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                onFailure("Error de red: ${t.message}")
            }
        })
    }
}
