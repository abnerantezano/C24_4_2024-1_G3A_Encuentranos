package com.ambrosio.josue.tutorial.ui.activities
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.ambrosio.josue.tutorial.data.models.ClienteModel
import com.ambrosio.josue.tutorial.data.models.ContratoModel
import com.ambrosio.josue.tutorial.data.models.NotificacionModel
import com.ambrosio.josue.tutorial.data.models.ProveedorModel
import com.ambrosio.josue.tutorial.databinding.ActivityAceptarOcancelarContratoBinding
import com.ambrosio.josue.tutorial.generals.HeaderInclude
import com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment.MiContratoActivity
import com.ambrosio.josue.tutorial.ui.viewModels.ContratoViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.NotificacionViewModel

class AceptarOCancelarContratoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAceptarOcancelarContratoBinding
    private val contratoViewModel: ContratoViewModel by viewModels()
    private val notificacionViewModel: NotificacionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAceptarOcancelarContratoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los datos del intent

        val nombreCliente = intent.getStringExtra("NOMBRE_CLIENTE")
        val nombreServicio = intent.getStringExtra("NOMBRE_SERVICIO")
        val precioActual = intent.getStringExtra("PRECIO_ACTUAL")
        val estadoServicio = intent.getStringExtra("ESTADO_SERVICIO")

        binding.tvNombreCliente.text = nombreCliente
        binding.tvNombreServicio.text = nombreServicio
        binding.tvPrecioActual.text = precioActual
        binding.tvEstadoDetalleContrato.text = estadoServicio

        // Configurar acciones de botones
        aceptarContrato()
        denegarContrato()
        setupHeader()
    }

    private fun aceptarContrato() {
        binding.btnAceptarContrato.setOnClickListener {
            showConfirmationDialog("¿Estás seguro de aceptar este contrato?", true)
        }
    }

    private fun denegarContrato() {
        binding.btnDenegarContrato.setOnClickListener {
            showConfirmationDialog("¿Estás seguro de denegar este contrato?", false)
        }
    }

    private fun showConfirmationDialog(message: String, aceptar: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmación")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val idContrato = intent.getIntExtra("ID_CONTRATO", 0)
            val idProveedor = intent.getIntExtra("ID_PROVEEDOR", -1)
            val idCliente = intent.getIntExtra("ID_CLIENTE", -1)

            if (aceptar) {
                contratoViewModel.aceptarContratoProveedor(idContrato)
                val notificacion = NotificacionModel(
                    idNotificacion = 0,
                    idCliente = ClienteModel(idCliente!!),
                    idProveedor = ProveedorModel(idProveedor),
                    idContrato = ContratoModel(idContrato),
                    titulo = "Respuesta contrato",
                    mensaje = "El proveedor acepto tu contrato",
                    estado = "visto"
                )
                // Crear notificación
                notificacionViewModel.agregarNotificacion(idCliente!!, idProveedor, idContrato, notificacion)

            } else {
                contratoViewModel.denegarContratoProveedor(idContrato)
                val notificacion = NotificacionModel(
                    idNotificacion = 0,
                    idCliente = ClienteModel(idCliente!!),
                    idProveedor = ProveedorModel(idProveedor),
                    idContrato = ContratoModel(idContrato),
                    titulo = "Respuesta contrato",
                    mensaje = "El proveedor cancelo tu contrato",
                    estado = "visto"
                )
                // Crear notificación
                notificacionViewModel.agregarNotificacion(idCliente!!, idProveedor, idContrato, notificacion)

            }
            navigateToMiContratoActivity()
        }
        builder.setNegativeButton("Cancelar", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun navigateToMiContratoActivity() {
        val intent = Intent(this, MiContratoActivity::class.java)
        startActivity(intent)
        finish() // Opcional, dependiendo de si quieres que esta actividad se mantenga en la pila o no
    }

    private fun setupHeader() {
        // Aquí puedes configurar el encabezado de la actividad si es necesario
    }
}
