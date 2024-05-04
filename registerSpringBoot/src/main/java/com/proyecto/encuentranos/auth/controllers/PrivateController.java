package com.proyecto.encuentranos.auth.controllers;

import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.repositorios.ITipoUsuarioRepositorio;
import com.proyecto.encuentranos.repositorios.IUsuarioRepositorio;
import com.proyecto.encuentranos.servicios.ClienteServicio;
import com.proyecto.encuentranos.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PrivateController {

    @Autowired
    private IUsuarioRepositorio usuarioRepository;

    @Autowired
    private ITipoUsuarioRepositorio tipoUsuarioRepository;

    @Autowired
    private UsuarioServicio usuarioServicio; // Inyectar el servicio de usuario
    
    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/messages")
    public String privateMessages(@RequestParam(name = "tipoUsuarioId", required = false, defaultValue = "1") Long tipoUsuarioId, @AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        String email = oauth2User.getAttribute("email");
        Optional<UsuarioModelo> usuarioOptional = usuarioRepository.findByCorreo(email);

        if (usuarioOptional.isPresent()) {
            UsuarioModelo usuario = usuarioOptional.get();
            model.addAttribute("usuario", usuario);
            model.addAttribute("correo", usuario.getCorreo());
            model.addAttribute("contrasena", usuario.getContrasena());
            model.addAttribute("idTipo", usuario.getIdTipo().getId());
            System.out.println("response");
            return "response";
        } else {
            // Si el usuario no existe, redirigir al formulario para agregar un nuevo usuario
            model.addAttribute("correo", email);
            UsuarioModelo nuevoUsuario = new UsuarioModelo();
            ClienteModelo nuevoCliente = new ClienteModelo();
            nuevoUsuario.setCorreo(email); // Establecer el correo electrónico como el correo con el que se inicia sesión
            
            TipoUsuarioModelo tipoUsuario = tipoUsuarioRepository.findById(tipoUsuarioId)
                            .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado"));

            nuevoUsuario.setIdTipo(tipoUsuario); // Establecer el idTipo como 1 para cliente o 2 para proveedor
            model.addAttribute("usuario", nuevoUsuario);
            model.addAttribute("cliente", nuevoCliente);
            System.out.println("Pasar a formulario");
            return "agregarUsuarioFormulario";
        }
    }



    @PostMapping("/agregar-usuario")
    public String agregarUsuario(@ModelAttribute UsuarioModelo usuarioModelo, @ModelAttribute ClienteModelo clienteModelo, Model model) {
        // Guardar el nuevo usuario en la base de datos usando el método del servicio UsuarioServicio
        UsuarioModelo nuevoUsuario = usuarioServicio.guardarUsuario(usuarioModelo);
        clienteModelo.setIdUsuario(nuevoUsuario); // Establecer el usuario para el cliente
        ClienteModelo nuevoCliente = clienteServicio.guardarCliente(clienteModelo);
        model.addAttribute("usuario", nuevoUsuario);
        model.addAttribute("correo", nuevoUsuario.getCorreo());
        model.addAttribute("contrasena", nuevoUsuario.getContrasena());
        model.addAttribute("idTipo", nuevoUsuario.getIdTipo().getId());
        model.addAttribute("cliente", nuevoCliente);
        model.addAttribute("apellidoPaterno", nuevoCliente.getApellidoPaterno());
        model.addAttribute("apellidoMaterno", nuevoCliente.getApellidoMaterno());
        model.addAttribute("sexo", nuevoCliente.getSexo());
        model.addAttribute("fechaNacimiento", nuevoCliente.getFechaNacimiento());
        model.addAttribute("imagenUrl", nuevoCliente.getImagenUrl());
        model.addAttribute("celular", nuevoCliente.getCelular());
        model.addAttribute("distrito", nuevoCliente.getDistrito());
        model.addAttribute("dni", nuevoCliente.getDni());
        model.addAttribute("nombre", nuevoCliente.getNombre());
        model.addAttribute("provincia", nuevoCliente.getProvincia());
        model.addAttribute("region", nuevoCliente.getRegion());
        model.addAttribute("idUsuario", nuevoCliente.getIdUsuario().getId());

        return "response";
    }
}
