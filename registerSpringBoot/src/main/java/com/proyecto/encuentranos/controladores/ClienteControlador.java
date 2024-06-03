package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
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

//ESTAMOS CREANDO EL CONTROLADOR PARA Cliente
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/cliente")
public class ClienteControlador {
    
    //INSTANCIAR LAS CLASES QUE USAREMOS
    @Autowired
    private ClienteServicio clienteServicio;
    
    //AGREGAR UN CLIENTE
    @PostMapping("/agregar")
    public ResponseEntity<ClienteModelo> guardarCliente(@RequestBody ClienteModelo cliente) {
        ClienteModelo nuevoCliente = clienteServicio.guardarCliente(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }
    
    //LISTAR TODOS LOS CLIENTES
    @GetMapping("/listar")
    public ResponseEntity<ArrayList<ClienteModelo>> obtenerClientes() {
        ArrayList<ClienteModelo> clientes = clienteServicio.obtenerClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    
    //ACTUALIZAR UN CLIENTE 
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ClienteModelo> actualizarCliente(@RequestBody ClienteModelo request, @PathVariable("id") Integer id) {
        ClienteModelo clienteActualizado = clienteServicio.actualizarCliente(id, request);
        if (clienteActualizado != null) {
            return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    //BUSCAR UN CLIENTE POR SU ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<ClienteModelo>> encontrarClientePorId(@PathVariable Integer id) {
        Optional<ClienteModelo> cliente = clienteServicio.encontrarClientePorId(id);
        if (cliente.isPresent()) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //BUSCAR PROVEEDORES DEL MISMO DISTRITO DEL CLIENTE
    @GetMapping("/buscar-proveedores/{idCliente}")
    public ResponseEntity<List<ProveedorModelo>> buscarProveedoresPorDistrito(@PathVariable Integer idCliente) {
        List<ProveedorModelo> proveedores = clienteServicio.encontrarPrestadoresDeMiDistrito(idCliente);
        if (!proveedores.isEmpty()) {
            return new ResponseEntity<>(proveedores, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    //BUSCAR SERVICIOS DEL MISMO DISTRITO DEL CLIENTE
    @GetMapping("/buscar-servicios/{idCliente}")
    public ResponseEntity<List<ServicioProveedorModelo>> encontrarServiciosDeMiDistrito(@PathVariable Integer idCliente) {
        List<ServicioProveedorModelo> servicios = clienteServicio.encontrarServiciosDeMiDistrito(idCliente);
        if (!servicios.isEmpty()) {
            return new ResponseEntity<>(servicios, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
