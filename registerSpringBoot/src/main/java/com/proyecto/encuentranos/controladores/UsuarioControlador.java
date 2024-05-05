package com.proyecto.encuentranos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.servicios.ClienteServicio;
import com.proyecto.encuentranos.servicios.UsuarioServicio;
<<<<<<< Updated upstream
@CrossOrigin(origins = "http://localhost:3000")
=======

@CrossOrigin(origins = "http://localhost:3000/")
>>>>>>> Stashed changes
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;
    
    @Autowired
    ClienteServicio clienteServicio;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuarioModelo", new UsuarioModelo());
        model.addAttribute("clienteModelo", new ClienteModelo());
        return "agregarUsuarioFormulario";
    }

    @PostMapping("/registro")
    public String guardarUsuario(@ModelAttribute UsuarioModelo usuarioModelo, @ModelAttribute ClienteModelo clienteModelo, Model model) {
        // Establecer el tipo de usuario por defecto como 1
        TipoUsuarioModelo tipoUsuario = new TipoUsuarioModelo(1L);
        usuarioModelo.setIdTipo(tipoUsuario);

        // Guardar el nuevo usuario
        UsuarioModelo nuevoUsuario = usuarioServicio.guardarUsuario(usuarioModelo);

        // Asignar el usuario al cliente
        clienteModelo.setIdUsuario(nuevoUsuario);

        // Guardar el nuevo cliente
        clienteServicio.guardarCliente(clienteModelo);

        // Agregar los atributos al modelo
        model.addAttribute("usuario", nuevoUsuario);
        model.addAttribute("correo", nuevoUsuario.getCorreo());
        model.addAttribute("contrasena", nuevoUsuario.getContrasena());
        model.addAttribute("idTipo", nuevoUsuario.getIdTipo().getId());

        return "response";
    }
}
