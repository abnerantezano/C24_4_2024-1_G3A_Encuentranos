package com.proyecto.encuentranos.controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.servicios.ClienteServicio;
import com.proyecto.encuentranos.servicios.ProveedorServicio;
import com.proyecto.encuentranos.servicios.UsuarioServicio;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@Controller
@RequestMapping("/registro")
public class RegisterController {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;
    
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarUsuarioDependiente(@RequestBody UsuarioModelo usuario) {
        if (usuario != null && usuario.getIdTipo() != null) {
            if (usuario.getIdTipo().getId() == 1) { // Si el usuario es cliente
                ClienteModelo cliente = new ClienteModelo();
                cliente.setIdUsuario(usuario);
                ClienteModelo nuevoCliente = clienteServicio.guardarCliente(cliente);
                
                return ResponseEntity.ok(nuevoCliente);
            } else if (usuario.getIdTipo().getId() == 2) { 
                ProveedorModelo proveedor = new ProveedorModelo();
                proveedor.setIdUsuario(usuario);
                ProveedorModelo nuevoProveedor = proveedorServicio.guardarProveedor(proveedor);

                return ResponseEntity.ok(nuevoProveedor);
            } else {
                return ResponseEntity.badRequest().body("Tipo de usuario inválido");
            }
        } else {
            return ResponseEntity.badRequest().body("Usuario inválido");
        }
    }
    
    @PostMapping("/agregarpordefecto")
    public ResponseEntity<?> agregarUsuarioPorDefecto(@RequestBody UsuarioModelo usuario) {
        if (usuario != null) {
            if (usuario.getIdTipo() == null) {
                TipoUsuarioModelo tipoUsuario = new TipoUsuarioModelo(1L);
                usuario.setIdTipo(tipoUsuario);
            }
            if (usuario.getCorreo() == null) {
                usuario.setCorreo(usuario.getId() + "@example.com");
            }
            if (usuario.getContrasena() == null) {
                usuario.setContrasena("123456");
            }
            
            if (usuario.getIdTipo().getId() == 1) { // Si el usuario es cliente
                ClienteModelo cliente = new ClienteModelo();
                cliente.setIdUsuario(usuario);
                ClienteModelo nuevoCliente = clienteServicio.guardarCliente(cliente);
                
                return ResponseEntity.ok(nuevoCliente);
            } else if (usuario.getIdTipo().getId() == 2) { 
                ProveedorModelo proveedor = new ProveedorModelo();
                proveedor.setIdUsuario(usuario);
                ProveedorModelo nuevoProveedor = proveedorServicio.guardarProveedor(proveedor);

                return ResponseEntity.ok(nuevoProveedor);
            } else {
                return ResponseEntity.badRequest().body("Tipo de usuario inválido");
            }
        } else {
            return ResponseEntity.badRequest().body("Usuario inválido");
        }
    }

}
