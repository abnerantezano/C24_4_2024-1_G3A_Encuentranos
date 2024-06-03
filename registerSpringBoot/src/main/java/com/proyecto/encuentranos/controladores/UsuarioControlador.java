package com.proyecto.encuentranos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.servicios.ClienteServicio;
import com.proyecto.encuentranos.servicios.ProveedorServicio;
import com.proyecto.encuentranos.servicios.UsuarioServicio;

import java.util.Map;
import java.util.Optional;

//ESTAMOS CREANDO EL CONTROLADOR PARA Usuario
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    ClienteServicio clienteServicio;

    @Autowired
    ProveedorServicio proveedorServicio;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @PostMapping("/agregar")
    public UsuarioModelo guardarUsuario(@RequestBody UsuarioModelo usuario) {
        return usuarioServicio.guardarUsuario(usuario);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioServicio.obtenerUsuarios());
    }

    @GetMapping("/token")
    public ResponseEntity<?> obtenerTokenYEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute("email");
            OAuth2AccessToken accessToken = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(),
                    oauthToken.getName()
            ).getAccessToken();
            return ResponseEntity.ok(Map.of("token", accessToken.getTokenValue(), "email", email));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not an OAuth2 authentication");
    }

    @GetMapping("/verificar/{correo}")
    public ResponseEntity<?> buscarUsuarioPorCorreo(@PathVariable String correo) {
        Optional<UsuarioModelo> usuario = usuarioServicio.buscarUsuarioPorCorreo(correo);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/datos")
    public ResponseEntity<?> listarUsuarioConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute("email");
            Optional<UsuarioModelo> usuario = usuarioServicio.buscarUsuarioPorCorreo(email);
            return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().body("Not an OAuth2 authentication");
    }

    @GetMapping("/datossi")
    public ResponseEntity<?> listarClienteOProveedor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute("email");
            Optional<UsuarioModelo> usuarioOptional = usuarioServicio.buscarUsuarioPorCorreo(email);
            if (usuarioOptional.isPresent()) {
                UsuarioModelo usuario = usuarioOptional.get();
                Optional<ClienteModelo> clienteOptional = clienteServicio.buscarClientePorUsuarioId(usuario.getIdUsuario());
                Optional<ProveedorModelo> proveedorOptional = proveedorServicio.buscarProveedorPorUsuarioId(usuario.getIdUsuario());
                return clienteOptional.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> proveedorOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body("Not an OAuth2 authentication");
        }
    }
}
