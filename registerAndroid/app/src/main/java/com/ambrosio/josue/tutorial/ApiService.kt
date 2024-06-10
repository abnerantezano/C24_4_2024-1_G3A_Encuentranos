package com.ambrosio.josue.tutorial

import com.ambrosio.josue.tutorial.models.*
import com.proyecto.encuentranos.models.DetalleContratoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("tipo-usuario/listar")
    fun listarTipoUsuarios(): Call<List<TipoUsuarioModel>>

    @GET("usuario/listar")
    fun listarUsuarios(): Call<List<UsuarioModel>>

    @GET("distrito/listar")
    fun listarDistritos(): Call<List<DistritoModel>>

    @POST("usuario/agregar")
    fun agregarUsuario(@Body usuario: UsuarioModel): Call<UsuarioModel>

    @POST("proveedor/agregar")
    fun agregarProveedor(@Body proveedor: ProveedorModel): Call<ProveedorModel>

    @GET("servicio/listar")
    fun listarServicios(): Call<List<ServicioModel>>

    @GET("servicio-proveedor/listar")
    fun listarServiciosProveedores(): Call<List<ServicioProveedorModel>>

    @POST("servicio-proveedor/agregar")
    fun agregarServicioProveedor(@Body serviciosProveedor: List<ServicioProveedor>): Call<List<ServicioProveedor>>

    @GET("usuario/verificar/{correo}")
    fun verificarUsuarioPorCorreo(@Path("correo") correo: String): Call<UsuarioModel>

    @GET("detalle-contrato/listar")
    fun listarDetallesContratos(): Call<List<DetalleContratoModel>>
}
