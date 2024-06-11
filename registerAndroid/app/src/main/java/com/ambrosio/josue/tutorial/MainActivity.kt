package com.ambrosio.josue.tutorial
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.ambrosio.josue.tutorial.databinding.ActivityMainBinding
import com.ambrosio.josue.tutorial.fragments.CuartoFragment
import com.ambrosio.josue.tutorial.fragments.InicioFragment
import com.ambrosio.josue.tutorial.fragments.PerfilFragment
import com.ambrosio.josue.tutorial.fragments.ServiciosFragment
import com.ambrosio.josue.tutorial.fragments.TerceroFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iconHome.setOnClickListener {
            showFragment(InicioFragment())
        }
        binding.iconLimpieza.setOnClickListener {
            showFragment(ServiciosFragment())
        }
        binding.iconTarjeta.setOnClickListener {
            showFragment(TerceroFragment())
        }
        binding.iconChat.setOnClickListener {
            showFragment(CuartoFragment())
        }
        binding.iconUsuario.setOnClickListener {
            showFragment(PerfilFragment())
        }

        // Mostrar el fragmento de inicio por defecto al iniciar la actividad
        if (savedInstanceState == null) {
            showFragment(InicioFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null) // Agregar a la pila de retroceso
        }
    }
}
