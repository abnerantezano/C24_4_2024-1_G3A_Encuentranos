package com.proyecto.encuentranos.controladores;

import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.repositorios.ITipoUsuarioRepositorio;
import com.proyecto.encuentranos.repositorios.IUsuarioRepositorio;
import com.proyecto.encuentranos.servicios.ClienteServicio;
import com.proyecto.encuentranos.servicios.ProveedorServicio;
import com.proyecto.encuentranos.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@Controller
@RequestMapping("/logini")
public class PrivateController {

    @Autowired
    private IUsuarioRepositorio usuarioRepository;

    @Autowired
    private ITipoUsuarioRepositorio tipoUsuarioRepository;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/messages")
    public String privateMessages(@RequestParam(name = "tipoUsuarioId", required = false, defaultValue = "1") Long tipoUsuarioId, @AuthenticationPrincipal OAuth2User oauth2User, Model model, HttpServletRequest request) {
        String email = oauth2User.getAttribute("email");
        Optional<UsuarioModelo> usuarioOptional = usuarioRepository.findByCorreo(email);
        
        // Verificar si el usuario ya tiene un tipo de usuario asignado
        Long tipoUsuarioRegistrado = (Long) request.getSession().getAttribute("tipoUsuario");
        if (tipoUsuarioRegistrado != null) {
            // Si el usuario ya tiene un tipo de usuario registrado y trata de cambiarlo, redirígelo a la página de inicio o muestra un mensaje de error
            if (tipoUsuarioId != tipoUsuarioRegistrado) {
                return "redirect:/error";
            }
        }

        //Si el usuario ya existe entonces te pandara a la pagina response
        if (usuarioOptional.isPresent()) {
            UsuarioModelo usuario = usuarioOptional.get();
            model.addAttribute("usuario", usuario);
            model.addAttribute("correo", usuario.getCorreo());
            model.addAttribute("contrasena", usuario.getContrasena());
            model.addAttribute("idTipo", usuario.getIdTipo().getId());

            // Si el usuario no tiene un tipo de usuario registrado, almacena el tipo de usuario actual en la sesión
            if (tipoUsuarioRegistrado == null) {
                request.getSession().setAttribute("tipoUsuario", usuario.getIdTipo().getId());
            }

            return "response";//Aqui esta redireccionando a el html response que he realizado para probar : templates/response
        } else {
            // Si el usuario no existe, redirigir al formulario para agregar un nuevo usuario
            model.addAttribute("correo", email);
            UsuarioModelo nuevoUsuario = new UsuarioModelo();
            nuevoUsuario.setCorreo(email); // Establecer el correo electrónico como el correo con el que se inicia sesión

            TipoUsuarioModelo tipoUsuario = tipoUsuarioRepository.findById(tipoUsuarioId)
                    .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado"));

            nuevoUsuario.setIdTipo(tipoUsuario); // Establecer el idTipo como 1 para cliente o 2 para proveedor
            model.addAttribute("usuario", nuevoUsuario);
            if (tipoUsuarioId == 1) {
                // Cliente
                model.addAttribute("cliente", new ClienteModelo());
                return "redirect:http://localhost:3000/crud";
            } else if (tipoUsuarioId == 2) {
                // Proveedor
                model.addAttribute("proveedor", new ProveedorModelo());
                return "redirect:http://localhost:3000/crud";
            } else {
                // Tipo de usuario no válido
                return "error";
            }
        }
    }

	@PostMapping("/agregar")
	public UsuarioModelo guardarUsuario(@RequestBody UsuarioModelo usuario) {
		return this.usuarioServicio.guardarUsuario(usuario);
	}

}
