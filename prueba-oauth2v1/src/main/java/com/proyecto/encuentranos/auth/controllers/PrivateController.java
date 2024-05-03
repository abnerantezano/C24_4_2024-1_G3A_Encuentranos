package com.proyecto.encuentranos.auth.controllers;

import com.proyecto.encuentranos.auth.models.TipoUsuarioModelo;
import com.proyecto.encuentranos.auth.models.UsuarioModelo;
import com.proyecto.encuentranos.auth.repositories.ITipoUsuarioRepositorio;
import com.proyecto.encuentranos.auth.repositories.IUsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PrivateController {

    @Autowired
    private IUsuarioRepositorio usuarioRepository;

    @Autowired
    private ITipoUsuarioRepositorio tipoUsuarioRepository;

    @GetMapping("/messages")
    public String privateMessages(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        String email = oauth2User.getAttribute("email");
        Optional<UsuarioModelo> usuarioOptional = usuarioRepository.findByCorreo(email);

        if (usuarioOptional.isPresent()) {
            UsuarioModelo usuario = usuarioOptional.get();
            model.addAttribute("correo", usuario.getCorreo());
            model.addAttribute("contraseña", usuario.getContraseña());
            model.addAttribute("idTipo", usuario.getIdTipo().getId());
        } else {
            Optional<TipoUsuarioModelo> tipoUsuarioOptional = tipoUsuarioRepository.findById(1L); // Obtener el tipo de usuario por defecto
            if (tipoUsuarioOptional.isPresent()) {
                UsuarioModelo usuario = new UsuarioModelo();
                usuario.setCorreo(email);
                usuario.setContraseña("123456"); // Contraseña por defecto
                usuario.setIdTipo(tipoUsuarioOptional.get()); // Establecer el tipo de usuario por defecto
                usuarioRepository.save(usuario); // Guardar el nuevo usuario en la base de datos
                model.addAttribute("correo", email);
                model.addAttribute("contraseña", "123456");
                model.addAttribute("idTipo", 1); // ID del tipo de usuario por defecto
            } else {
                // Manejar el caso cuando no se encuentra el tipo de usuario por defecto
                return "error"; // Puedes crear una página de error y redirigir a ella
            }
        }
        return "response";
    }
    
    @GetMapping("/update-password")
    public String showUpdatePasswordForm(Model model) {
        model.addAttribute("usuario", new UsuarioModelo());
        return "update-password";
    }

    @PostMapping("/update-password")
    public String updatePassword(@AuthenticationPrincipal OAuth2User oauth2User, UsuarioModelo usuario) {
        String email = oauth2User.getAttribute("email");
        Optional<UsuarioModelo> usuarioOptional = usuarioRepository.findByCorreo(email);

        if (usuarioOptional.isPresent()) {
            UsuarioModelo usuarioToUpdate = usuarioOptional.get();
            usuarioToUpdate.setContraseña(usuario.getContraseña());
            usuarioRepository.save(usuarioToUpdate);
        }
        return "redirect:/messages";
    }
}
