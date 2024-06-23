package com.proyecto.encuentranos.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.servicios.ClienteServicio;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/cliente")
public class ClienteControlador {
    
    private final ClienteServicio clienteServicio;

    @Autowired
    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    @PostMapping("/agregar")
    public ResponseEntity<ClienteModelo> guardarCliente(@RequestBody ClienteModelo cliente) {
        ClienteModelo nuevoCliente = clienteServicio.guardarCliente(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<ClienteModelo>> obtenerClientes() {
        List<ClienteModelo> clientes = clienteServicio.obtenerClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ClienteModelo> actualizarCliente(@RequestBody ClienteModelo request, @PathVariable("id") Integer id) {
        ClienteModelo clienteActualizado = clienteServicio.actualizarCliente(id, request);
        if (clienteActualizado != null) {
            return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<ClienteModelo>> encontrarClientePorId(@PathVariable Integer id) {
        Optional<ClienteModelo> cliente = clienteServicio.encontrarClientePorId(id);
        if (cliente.isPresent()) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-proveedores/distrito/{idCliente}")
    public ResponseEntity<List<ProveedorModelo>> buscarProveedoresPorDistrito(@PathVariable Integer idCliente) {
        List<ProveedorModelo> proveedores = clienteServicio.encontrarPrestadoresDeMiDistrito(idCliente);
        if (!proveedores.isEmpty()) {
            return new ResponseEntity<>(proveedores, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/buscar-servicios/distrito/{idCliente}")
    public ResponseEntity<List<ServicioProveedorModelo>> encontrarServiciosDeMiDistrito(@PathVariable Integer idCliente) {
        List<ServicioProveedorModelo> servicios = clienteServicio.encontrarServiciosDeMiDistrito(idCliente);
        if (!servicios.isEmpty()) {
            return new ResponseEntity<>(servicios, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
	//BUSCAR CLIENTE POR ID DEL USUARIO
	@GetMapping("/buscar-usuario/{idUsuario}")
	public ResponseEntity<Optional<ClienteModelo>> encontrarProveedorPorIdUsuario(@PathVariable Integer idUsuario) {
	    Optional<ClienteModelo> clienteEncontrado = clienteServicio.buscarClientePorUsuarioId(idUsuario);
	    if (clienteEncontrado.isPresent()) {
	        return new ResponseEntity<>(clienteEncontrado, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
    
}
