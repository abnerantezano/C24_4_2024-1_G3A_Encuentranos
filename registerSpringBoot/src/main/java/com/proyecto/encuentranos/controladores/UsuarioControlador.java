package com.proyecto.encuentranos.controladores;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.servicios.ClienteServicio;
import com.proyecto.encuentranos.servicios.ProveedorServicio;
import com.proyecto.encuentranos.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
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
    
    @GetMapping("/buscar/{idUsuario}")
    public ResponseEntity<UsuarioModelo> buscarUsuarioPorId(@PathVariable int idUsuario){
        Optional<UsuarioModelo> usuario = usuarioServicio.buscarUsuarioPorId(idUsuario);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/verificar/{correo}")
    public ResponseEntity<UsuarioModelo> buscarUsuarioPorCorreo(@PathVariable String correo, @RequestHeader("Authorization") String token) {
        // Validate the token
        if (!validarToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<UsuarioModelo> usuario = usuarioServicio.buscarUsuarioPorCorreo(correo);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    private boolean validarToken(String token) {
        // Try to validate Firebase token first
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token.replace("Bearer ", ""));
            if (decodedToken != null) {
                System.out.println("Firebase token validado correctamente.");
                return true;
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }

        // If Firebase token validation fails, handle OAuth2 token validation
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

            if (authorizedClient != null) {
                OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
                if (accessToken != null && accessToken.getTokenValue().equals(token)) {
                    Instant now = Instant.now();
                    Instant tokenExpiration = accessToken.getExpiresAt();
                    boolean tokenIsValid = tokenExpiration.isAfter(now);
                    System.out.println("OAuth2 token es válido: " + tokenIsValid);
                    return tokenIsValid;
                }
            }
        }

        System.out.println("Token no válido.");
        return false;
    }



    @GetMapping("/datos")
    public ResponseEntity<UsuarioModelo> listarUsuarioConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute(EMAIL_ATTRIBUTE);
            UsuarioModelo usuario = usuarioServicio.buscarUsuarioPorCorreo(email).orElse(null);
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/proveedor/datos")
    public ResponseEntity<ProveedorModelo> listarProveedorConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute(EMAIL_ATTRIBUTE);
            UsuarioModelo usuario = usuarioServicio.buscarUsuarioPorCorreo(email).orElse(null);
            if (usuario != null) {
                ProveedorModelo proveedor = proveedorServicio.buscarProveedorPorCorreo(email).orElse(null);
                return ResponseEntity.ok(proveedor);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/cliente/datos")
    public ResponseEntity<ClienteModelo> listarClienteConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute(EMAIL_ATTRIBUTE);
            UsuarioModelo usuario = usuarioServicio.buscarUsuarioPorCorreo(email).orElse(null);
            if (usuario != null) {
                ClienteModelo cliente = clienteServicio.buscarClientePorCorreo(email).orElse(null);
                return ResponseEntity.ok(cliente);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
