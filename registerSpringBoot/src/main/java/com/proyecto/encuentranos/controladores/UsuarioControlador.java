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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
//ESTAMOS CREANDO EL CONTROLADOR PARA Usuario
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

	//INSTANCIAR LAS CLASES QUE USAREMOS

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    ClienteServicio clienteServicio;
    
    @Autowired
    ProveedorServicio proveedorServicio;
    
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    
    //AGREGAR UN USUARIO
	@PostMapping("/agregar")
	public UsuarioModelo guardarUsuario(@RequestBody UsuarioModelo usuario) {
		return this.usuarioServicio.guardarUsuario(usuario);
	}
	
	//LISTAR UN USUARIO
    @GetMapping("/listar")
    public ArrayList<UsuarioModelo> obtenerUsuarios() {
    	return (ArrayList<UsuarioModelo>) usuarioServicio.obtenerUsuarios();
    }
    
    //OBTENER EL TOKEN DE UN USUARIO
	@GetMapping("/token")
	public ResponseEntity<Map<String, String>> obtenerTokenYEmail() {
		// OBTENER LA AUTENTICACION ACTUAL
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// CREAR UN MAPA PARA ALMACENAR EL TOKEN Y EL EMAIL
		Map<String, String> tokenAndEmail = new HashMap<>();
		
		// VERIFICAR SI LA AUTENTICACION ES DE TIPO OAUTH2
		if (authentication instanceof OAuth2AuthenticationToken) {
			// OBTENER EL USUARIO AUTENTICADO
			OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
			OAuth2User oauth2User = oauthToken.getPrincipal();
			
			// OBTENER LOS ATRIBUTOS DEL USUARIO
			String email = (String) oauth2User.getAttribute("email");

			// OBTENER EL TOKEN DE ACCESO USANDO OAuth2AuthorizedClientService
			OAuth2AccessToken accessToken = authorizedClientService.loadAuthorizedClient(
					oauthToken.getAuthorizedClientRegistrationId(),
					oauthToken.getName()
			).getAccessToken();
			
			// AGREGAR EL TOKEN Y EL EMAIL
			tokenAndEmail.put("token", accessToken.getTokenValue());
			tokenAndEmail.put("email", email);

			// DEVOLVER EL TOKEN Y EL EMAIL
			return ResponseEntity.ok(tokenAndEmail);
		}
		
		// NULO SI NO ES TIPO OAUTH2
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	//VERIFICAR QUE EXISTE UN USUARIO POR SU CORREO
	@GetMapping("/verificar/{correo}")
	public Optional<UsuarioModelo> buscarUsuarioPorCorreo(@PathVariable String correo) {
		Optional<UsuarioModelo> usuario = usuarioServicio.buscarUsuarioPorCorreo(correo);
		if (usuario.isEmpty()) {
			return null;
		}
		return usuario;
	}
	
	//OBTENER LOS DATOS DE UN USUARIO
	@GetMapping("/datos")
	public Optional<UsuarioModelo> listarUsuarioConectado() {
		// OBTENER LA AUTENTICACION ACTUAL
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// VERIFICAR QUE LA AUTENTICACION SEA OAUTH2
		if (authentication instanceof OAuth2AuthenticationToken) {
			
			// OBTENER EL USUARIO AUTENTICADO
			OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
			OAuth2User oauth2User = oauthToken.getPrincipal();
			
			// OBTENER EL CORREO ELECTRONICO
			String email = (String) oauth2User.getAttribute("email");
			
			// BUSCAR EL USUARIO POR EL CORREO ELECTRONICO
			Optional<UsuarioModelo> usuario = usuarioServicio.buscarUsuarioPorCorreo(email);
			return usuario;
		}
		
		// NULO SI NO ES OAUTH2
		return null;
	}
	
	//OBTENER LOS DATOS DE UN CLIENTE O PROVEEDOR
	@GetMapping("/datossi")
	public ResponseEntity<?> listarClienteOProveedor() {
	    // OBTENER LA AUTENTICACION ACTUAL
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    // VERIFICAR QUE SAE TIPO OAuth2
	    if (authentication instanceof OAuth2AuthenticationToken) {
	        // OBTENER EL USUARIO
	        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
	        OAuth2User oauth2User = oauthToken.getPrincipal();
	        
	        // OBTENER EL CORREO
	        String email = (String) oauth2User.getAttribute("email");
	        
	        // BUSCAR EL USUARIO POR EL CORREO ELECTRONICO
	        Optional<UsuarioModelo> usuarioOptional = usuarioServicio.buscarUsuarioPorCorreo(email);
	        
	        if(usuarioOptional.isPresent()) {
	            UsuarioModelo usuario = usuarioOptional.get();
	            
	            // BUSCAR CLIENTE ASOCIADO AL USUARIO
	            Optional<ClienteModelo> clienteOptional = clienteServicio.buscarClientePorUsuarioId(usuario.getId());
	            
	            // BUSCAR PROVEEDOR ASOCIADO AL USUARIO
	            Optional<ProveedorModelo> proveedorOptional = proveedorServicio.buscarProveedorPorUsuarioId(usuario.getId());
	            
	            if(clienteOptional.isPresent()) {
	                return ResponseEntity.ok(clienteOptional.get());
	            } else if(proveedorOptional.isPresent()) {
	                return ResponseEntity.ok(proveedorOptional.get());
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } else {
	        return ResponseEntity.badRequest().body("No es una autenticación OAuth2");
	    }
	 }
}
