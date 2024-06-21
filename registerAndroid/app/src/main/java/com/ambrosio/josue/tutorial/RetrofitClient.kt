package com.ambrosio.josue.tutorial

import com.ambrosio.josue.tutorial.servicios.ClienteApi
import com.ambrosio.josue.tutorial.servicios.DetalleContratoApi
import com.ambrosio.josue.tutorial.servicios.DistritoApi
import com.ambrosio.josue.tutorial.servicios.MensajeApi
import com.ambrosio.josue.tutorial.servicios.ProveedorApi
import com.ambrosio.josue.tutorial.servicios.ServicioApi
import com.ambrosio.josue.tutorial.servicios.ServicioProveedorApi
import com.ambrosio.josue.tutorial.servicios.UsuarioApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private const val BASE_URL = "http://192.168.100.13:4000/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(loggingInterceptor)
    }.build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    val distritoApi: DistritoApi by lazy { retrofit.create(DistritoApi::class.java) }
    val usuarioApi: UsuarioApi by lazy { retrofit.create(UsuarioApi::class.java) }
    val proveedorApi: ProveedorApi by lazy { retrofit.create(ProveedorApi::class.java) }
    val clienteApi: ClienteApi by lazy { retrofit.create(ClienteApi::class.java) }
    val servicioApi: ServicioApi by lazy { retrofit.create(ServicioApi::class.java) }
    val servicioProveedorApi: ServicioProveedorApi by lazy { retrofit.create(ServicioProveedorApi::class.java) }
    val detalleContratoAPi: DetalleContratoApi by lazy { retrofit.create(DetalleContratoApi::class.java) }
    val mensajeApi: MensajeApi by lazy { retrofit.create(MensajeApi::class.java) }

}