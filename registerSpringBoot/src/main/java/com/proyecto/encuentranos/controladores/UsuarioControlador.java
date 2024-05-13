package com.proyecto.encuentranos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.servicios.UsuarioServicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;
    
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    
    @GetMapping("/listar")
    public ArrayList<UsuarioModelo> obtenerUsuarios() {
    	return (ArrayList<UsuarioModelo>) usuarioServicio.obtenerUsuarios();
    }
    
	@PostMapping("/agregar")
	public UsuarioModelo guardarUsuario(@RequestBody UsuarioModelo usuario) {
		return this.usuarioServicio.guardarUsuario(usuario);
	}

	@GetMapping("/token")
	public ResponseEntity<Map<String, String>> obtenerTokenYEmail() {
		// Obtener la autenticación actual
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// Crear un mapa para almacenar el token y el email
		Map<String, String> tokenAndEmail = new HashMap<>();
		
		// Verificar si la autenticación es de tipo OAuth2
		if (authentication instanceof OAuth2AuthenticationToken) {
			// Obtener el usuario autenticado
			OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
			OAuth2User oauth2User = oauthToken.getPrincipal();
			
			// Obtener los atributos del usuario
			String email = (String) oauth2User.getAttribute("email");

			// Obtener el token de acceso usando OAuth2AuthorizedClientService
			OAuth2AccessToken accessToken = authorizedClientService.loadAuthorizedClient(
					oauthToken.getAuthorizedClientRegistrationId(),
					oauthToken.getName()
			).getAccessToken();
			
			// Agregar el token y el email al mapa
			tokenAndEmail.put("token", accessToken.getTokenValue());
			tokenAndEmail.put("email", email);

			// Devolver el token y el email en una respuesta ResponseEntity
			return ResponseEntity.ok(tokenAndEmail);
		}
		
		// Devolver null si la autenticación no es de tipo OAuth2
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
