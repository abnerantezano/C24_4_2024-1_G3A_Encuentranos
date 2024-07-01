package com.ambrosio.josue.tutorial.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.ClienteModel
import com.ambrosio.josue.tutorial.data.models.DistritoModel
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
<<<<<<< HEAD
import com.ambrosio.josue.tutorial.ui.adapters.FileRequestBody
import com.ambrosio.josue.tutorial.ui.adapters.toMultipart
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
=======
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
>>>>>>> 48a3a91734a4fbd86bdb91a628c8e16915903b54
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PerfilViewModel : ViewModel() {

    private val clienteApi = RetrofitClient.clienteApi
    private val proveedorApi = RetrofitClient.proveedorApi
    private val usuarioApi = RetrofitClient.usuarioApi

    private val _descripcionActualizada = MutableLiveData<Boolean>()
    val descripcionActualizada: LiveData<Boolean> get() = _descripcionActualizada

    private val _informacionActualizada = MutableLiveData<Boolean>()
    val informacionActualizada: LiveData<Boolean> get() = _informacionActualizada

    private val _imagenActualizada = MutableLiveData<Boolean>()
    val imagenActualizada: LiveData<Boolean> get() = _imagenActualizada


    fun actualizarDescripcionCliente(idCliente: Int, descripcion: String) {
        val cliente = ClienteModel(idCliente = idCliente, descripcion = descripcion)
        clienteApi.actualizarCliente(idCliente, cliente).enqueue(object : Callback<ClienteModel> {
            override fun onResponse(call: Call<ClienteModel>, response: Response<ClienteModel>) {
                _descripcionActualizada.value = response.isSuccessful
            }

            override fun onFailure(call: Call<ClienteModel>, t: Throwable) {
                _descripcionActualizada.value = false
            }
        })
    }

    fun actualizarDescripcionProveedor(idProveedor: Int, descripcion: String) {
        val proveedor = ProveedorModel(idProveedor = idProveedor, descripcion = descripcion)
        proveedorApi.actualizarProveedor(idProveedor, proveedor).enqueue(object : Callback<ProveedorModel> {
            override fun onResponse(call: Call<ProveedorModel>, response: Response<ProveedorModel>) {
                _descripcionActualizada.value = response.isSuccessful
            }

            override fun onFailure(call: Call<ProveedorModel>, t: Throwable) {
                _descripcionActualizada.value = false
            }
        })
    }

<<<<<<< HEAD
    fun uploadFileToServer(idUsuario: Int, usuario: UsuarioModel, fileName: String, requestBody: FileRequestBody) {
        val usuarioJson = Gson().toJson(usuario)
        val usuarioBody = usuarioJson.toRequestBody("application/json".toMediaTypeOrNull())

        usuarioApi.actualizarUsuario(idUsuario, requestBody.toMultipart(fileName), usuarioBody)
            .enqueue(object : Callback<UsuarioModel> {
                override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                    _descripcionActualizada.postValue(response.isSuccessful)
                }

                override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                    _descripcionActualizada.postValue(false)
                }
            })
=======
    fun actualizarImagenUsuario(usuario: UsuarioModel, imageFile: File) {
        val usuarioJson = createRequestBodyFromString(usuario.toJson())
        val imagePart = createMultipartBodyPart(imageFile, "imagen")

        usuarioApi.actualizarUsuario(usuarioJson, imagePart).enqueue(object : Callback<UsuarioModel> {
            override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                _descripcionActualizada.value = response.isSuccessful
            }

            override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                _descripcionActualizada.value = false
            }
        })
    }

    private fun createMultipartBodyPart(file: File, partName: String): MultipartBody.Part {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    private fun createRequestBodyFromString(value: String): RequestBody {
        return RequestBody.create("application/json".toMediaTypeOrNull(), value)
    }

    private fun UsuarioModel.toJson(): String {
        // Implementa la lógica para convertir el modelo UsuarioModel a JSON
        return """{
            "idUsuario": ${idUsuario},
            "idTipo": {"id": ${idTipo.idTipo}, "nombre": "${idTipo.nombre}"},
            "correo": "${correo}",
            "contrasena": "${contrasena}",
            "imageUrl": ${imageUrl?.let { "\"$it\"" }},
            "estado": "${estado}",
            "fechaCreacion": "${fechaCreacion}"
        }"""
>>>>>>> 48a3a91734a4fbd86bdb91a628c8e16915903b54
    }

    fun actualizarInformacionCliente(
        idCliente: Int,
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        fechaNacimiento: String,
        celular: String,
        idDistrito: DistritoModel,
        sexo: String
    ) {
        val cliente = ClienteModel(
            idCliente = idCliente,
            nombre = nombre,
            apellidoPaterno = apellidoPaterno,
            apellidoMaterno = apellidoMaterno,
            fechaNacimiento = fechaNacimiento,
            celular = celular,
            idDistrito = idDistrito,
            sexo = sexo
        )

        clienteApi.actualizarCliente(idCliente, cliente).enqueue(object : Callback<ClienteModel> {
            override fun onResponse(call: Call<ClienteModel>, response: Response<ClienteModel>) {
                _informacionActualizada.value = response.isSuccessful
            }

            override fun onFailure(call: Call<ClienteModel>, t: Throwable) {
                _informacionActualizada.value = false
            }
        })
    }


    fun actualizarInformacionProveedor(
        idProveedor: Int,
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        fechaNacimiento: String,
        celular: String,
        idDistrito: DistritoModel,
        sexo: String
    ) {
        val proveedor = ProveedorModel(
            idProveedor = idProveedor,
            nombre = nombre,
            apellidoPaterno = apellidoPaterno,
            apellidoMaterno = apellidoMaterno,
            fechaNacimiento = fechaNacimiento,
            celular = celular,
            idDistrito = idDistrito,
            sexo = sexo
        )
        proveedorApi.actualizarProveedor(idProveedor, proveedor).enqueue(object : Callback<ProveedorModel> {
            override fun onResponse(call: Call<ProveedorModel>, response: Response<ProveedorModel>) {
                _informacionActualizada.value = response.isSuccessful
            }

            override fun onFailure(call: Call<ProveedorModel>, t: Throwable) {
                _informacionActualizada.value = false
            }
        })
    }
}