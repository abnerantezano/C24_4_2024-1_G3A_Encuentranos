package com.proyecto.encuentranos.controladores;

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

import java.util.List;
import java.util.Map;
import java.util.Optional;

//ESTAMOS CREANDO EL CONTROLADOR PARA Usuario
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String TOKEN = "token";
    private static final String ERROR = "error";
    private static final String NOT_OAUTH2_AUTHENTICATION = "Not an OAuth2 authentication";

    private final UsuarioServicio usuarioServicio;
    private final ClienteServicio clienteServicio;
    private final ProveedorServicio proveedorServicio;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public UsuarioControlador(UsuarioServicio usuarioServicio, ClienteServicio clienteServicio,
                              ProveedorServicio proveedorServicio, OAuth2AuthorizedClientService authorizedClientService) {
        this.usuarioServicio = usuarioServicio;
        this.clienteServicio = clienteServicio;
        this.proveedorServicio = proveedorServicio;
        this.authorizedClientService = authorizedClientService;
    }

    @PostMapping("/agregar")
    public UsuarioModelo guardarUsuario(@RequestBody UsuarioModelo usuario) {
        return usuarioServicio.guardarUsuario(usuario);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioModelo>> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioServicio.obtenerUsuarios());
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> obtenerTokenYEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute(EMAIL_ATTRIBUTE);
            OAuth2AccessToken accessToken = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(),
                    oauthToken.getName()
            ).getAccessToken();
            return ResponseEntity.ok(Map.of(TOKEN, accessToken.getTokenValue(), EMAIL_ATTRIBUTE, email));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(ERROR, NOT_OAUTH2_AUTHENTICATION));
    }

    @GetMapping("/verificar/{correo}")
    public ResponseEntity<UsuarioModelo> buscarUsuarioPorCorreo(@PathVariable String correo) {
        Optional<UsuarioModelo> usuario = usuarioServicio.buscarUsuarioPorCorreo(correo);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/datos")
    public ResponseEntity<UsuarioModelo> listarUsuarioConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute(EMAIL_ATTRIBUTE);
            Optional<UsuarioModelo> usuario = usuarioServicio.buscarUsuarioPorCorreo(email);
            return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/datossi")
    public ResponseEntity<Object> listarClienteOProveedor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute(EMAIL_ATTRIBUTE);
            Optional<UsuarioModelo> usuarioOptional = usuarioServicio.buscarUsuarioPorCorreo(email);
            if (usuarioOptional.isPresent()) {
                UsuarioModelo usuario = usuarioOptional.get();
                Optional<ClienteModelo> clienteOptional = clienteServicio.buscarClientePorUsuarioId(usuario.getIdUsuario());
                if (clienteOptional.isPresent()) {
                    return ResponseEntity.ok((Object) clienteOptional.get());
                }
                Optional<ProveedorModelo> proveedorOptional = proveedorServicio.buscarProveedorPorUsuarioId(usuario.getIdUsuario());
                if (proveedorOptional.isPresent()) {
                    return ResponseEntity.ok((Object) proveedorOptional.get());
                }
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body(NOT_OAUTH2_AUTHENTICATION);
        }
    }
}
