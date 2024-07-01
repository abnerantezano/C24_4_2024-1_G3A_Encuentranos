package com.ambrosio.josue.tutorial.ui.activities.opcionesPerfilFragment
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ambrosio.josue.tutorial.databinding.ActivityCambiarContrasenaBinding
import com.ambrosio.josue.tutorial.ui.viewModels.InicioViewModel
import com.ambrosio.josue.tutorial.ui.viewModels.UsuarioViewModel

class CambiarContrasenaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCambiarContrasenaBinding
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val inicioViewModel: InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUpdateButton()
    }

    private fun setupUpdateButton() {
        binding.btnActualizarContrasena.setOnClickListener {
            val contrasenaActual = binding.edtContrasena.text.toString().trim()
            val nuevaContrasena = binding.edtNuevaContrasena.text.toString().trim()
            val confirmarContrasena = binding.edtConfirmarContrasena.text.toString().trim()

            // Validaciones simples
            if (contrasenaActual.isEmpty() || nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                // Mostrar mensaje de error o manejar la validación según tu aplicación
                return@setOnClickListener
            }

            // Verificar que la nueva contraseña coincida con la confirmación
            if (nuevaContrasena != confirmarContrasena) {
                // Mostrar mensaje de error de contraseñas no coinciden
                return@setOnClickListener
            }

            // Obtener el ID del usuario
            val idUsuario = inicioViewModel.idUsuario.value ?: run {
                // Manejar caso donde no se pudo obtener el ID del usuario
                return@setOnClickListener
            }

            // Llamar al ViewModel para actualizar la contraseña
            usuarioViewModel.actualizarContrasena(
                id = idUsuario,
                contrasenaActual = contrasenaActual,
                nuevaContrasena = nuevaContrasena,
                onSuccess = {
                    Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()
                },
                onFailure = { errorMessage ->
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
