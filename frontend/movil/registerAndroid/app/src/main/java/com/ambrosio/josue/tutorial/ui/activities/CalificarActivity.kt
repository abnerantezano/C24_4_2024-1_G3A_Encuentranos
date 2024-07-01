package com.ambrosio.josue.tutorial.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.data.models.CalificacionModel
import com.ambrosio.josue.tutorial.data.models.ClienteModel
import com.ambrosio.josue.tutorial.data.models.DetalleCalificacionModel
import com.ambrosio.josue.tutorial.databinding.ActivityCalificarBinding
import com.ambrosio.josue.tutorial.data.servicios.CalificacionApi
import com.ambrosio.josue.tutorial.data.servicios.DetalleCalificacionApi
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.ContratoModel
import com.ambrosio.josue.tutorial.data.models.DetalleContratoModeloId
import com.ambrosio.josue.tutorial.data.models.NotificacionModel
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.data.models.ServicioModel
import com.ambrosio.josue.tutorial.data.servicios.ContratoApi
import com.ambrosio.josue.tutorial.ui.viewModels.NotificacionViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalificarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalificarBinding
    private val calificacionApi: CalificacionApi = RetrofitClient.calificacionApi
    private val contratoApi: ContratoApi = RetrofitClient.contratoApi
    private val detalleCalificacionApi: DetalleCalificacionApi = RetrofitClient.detalleCalificacionApi
    private val notificacionViewModel: NotificacionViewModel by viewModels()

    private var idContrato: Int = 0
    private var idCliente: Int = 0
    private var idProveedor: Int = 0
    private var idServicio: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalificarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idContrato = intent.getIntExtra("ID_CONTRATO", 0)
        idCliente = intent.getIntExtra("ID_CLIENTE", 0)
        idProveedor = intent.getIntExtra("ID_PROVEEDOR", 0)
        idServicio = intent.getIntExtra("ID_SERVICIO", 0)

        setupViews()
    }

    private fun setupViews() {
        val btnEnviar: Button = binding.btnEnviar
        val edtNumero: EditText = binding.edtNumero
        val edtComentario: EditText = binding.edtComentario

        btnEnviar.setOnClickListener {
            val calificacion = edtNumero.text.toString().toIntOrNull()
            val comentario = edtComentario.text.toString()

            if (calificacion != null && comentario.isNotBlank()) {
                enviarCalificacion(calificacion, comentario)
            } else {
                Toast.makeText(this, "Por favor ingrese una calificación y comentario válidos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enviarCalificacion(calificacion: Int, comentario: String) {
        val nuevaCalificacion = CalificacionModel(0, ClienteModel(idCliente), calificacion, comentario)

        calificacionApi.agregarCalificacion(nuevaCalificacion).enqueue(object : Callback<CalificacionModel> {
            override fun onResponse(call: Call<CalificacionModel>, response: Response<CalificacionModel>) {
                if (response.isSuccessful) {
                    val calificacionCreada = response.body()
                    if (calificacionCreada != null) {
                        Toast.makeText(this@CalificarActivity, "Calificación agregada correctamente", Toast.LENGTH_SHORT).show()

                        // Agregar detalle de calificación
                        val detalleCalificacion = DetalleCalificacionModel(
                            DetalleContratoModeloId(idProveedor, idServicio, idContrato),
                            ProveedorModel(idProveedor),
                            ServicioModel(idServicio),
                            calificacionCreada
                        )

                        agregarDetalleCalificacion(detalleCalificacion)

                        // Actualizar estado del contrato localmente
                        actualizarEstadoContratoLocal(idContrato)

                        // Crear notificación
                        val notificacion = NotificacionModel(
                            idNotificacion = 0,
                            idCliente = ClienteModel(idCliente!!),
                            idProveedor = ProveedorModel(idProveedor),
                            idContrato = ContratoModel(idContrato),
                            titulo = "Nuevo contrato",
                            mensaje = "Se agrego una nueva calificación a tu perfil",
                            estado = "visto"
                        )
                        notificacionViewModel.agregarNotificacion(idCliente!!, idProveedor, idContrato, notificacion)
                    } else {
                        Toast.makeText(this@CalificarActivity, "Error al obtener la calificación creada", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@CalificarActivity, "Error al agregar calificación", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CalificacionModel>, t: Throwable) {
                Toast.makeText(this@CalificarActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun agregarDetalleCalificacion(detalleCalificacion: DetalleCalificacionModel) {
        detalleCalificacionApi.agregarDetalleCalificacion(detalleCalificacion).enqueue(object : Callback<DetalleCalificacionModel> {
            override fun onResponse(call: Call<DetalleCalificacionModel>, response: Response<DetalleCalificacionModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CalificarActivity, "Detalle de calificación agregado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CalificarActivity, "Error al agregar detalle de calificación", Toast.LENGTH_SHORT).show()
                }
                // No finalices la actividad aquí, porque primero debemos actualizar el estado del contrato
                // finish()
            }

            override fun onFailure(call: Call<DetalleCalificacionModel>, t: Throwable) {
                Toast.makeText(this@CalificarActivity, "Error de red al agregar detalle de calificación: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                // finish()
            }
        })
    }

    private fun actualizarEstadoContratoLocal(idContrato: Int) {
        contratoApi.listarContratos().enqueue(object : Callback<List<ContratoModel>> {
            override fun onResponse(call: Call<List<ContratoModel>>, response: Response<List<ContratoModel>>) {
                if (response.isSuccessful) {
                    val contratos = response.body()
                    val contratoActualizado = contratos?.find { it.idContrato == idContrato }
                    contratoActualizado?.estado = "Finalizado fin"

                    Toast.makeText(this@CalificarActivity, "Estado del contrato actualizado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CalificarActivity, "Error al obtener la lista de contratos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ContratoModel>>, t: Throwable) {
                Toast.makeText(this@CalificarActivity, "Error de red al obtener la lista de contratos: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
