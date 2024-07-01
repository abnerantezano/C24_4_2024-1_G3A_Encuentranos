package com.proyecto.encuentranos.controladores;

import java.util.List;
import java.util.Optional;

import com.proyecto.encuentranos.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.*;
import com.proyecto.encuentranos.servicios.DetalleContratoServicio;

@RestController
@RequestMapping("/detalle-contrato")
public class DetalleContratoControlador {

    private final DetalleContratoServicio detalleContratoServicio;
    private final ClienteServicio clienteServicio;

    @Autowired
    public DetalleContratoControlador(DetalleContratoServicio detalleContratoServicio, ClienteServicio clienteServicio) {
        this.detalleContratoServicio = detalleContratoServicio;
        this.clienteServicio = clienteServicio;
    }

    @PostMapping("/crear")
    public ResponseEntity<DetalleContratoModelo> crearDetalleContrato(@RequestBody DetalleContratoModelo detalleContrato) {
        DetalleContratoModelo nuevoDetalleContrato = detalleContratoServicio.crearDetalleContrato(detalleContrato);
        return ResponseEntity.ok(nuevoDetalleContrato);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DetalleContratoModelo>> obtenerDetalleContratos() {
        List<DetalleContratoModelo> detalles = detalleContratoServicio.obtenerDetalleContratos();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<DetalleContratoModelo>> obtenerDetalleContratoPorProveedor(@PathVariable Integer proveedorId) {
        ProveedorModelo proveedor = new ProveedorModelo();
        proveedor.setIdProveedor(proveedorId);
        List<DetalleContratoModelo> detalles = detalleContratoServicio.obtenerDetalleContratoPorProveedor(proveedor);
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/proveedor-pendientes/{proveedorId}")
    public ResponseEntity<List<DetalleContratoModelo>> obtenerDetalleContratoPorProveedorYEstadoPendiente(@PathVariable Integer proveedorId) {
        ProveedorModelo proveedor = new ProveedorModelo();
        proveedor.setIdProveedor(proveedorId);
        List<DetalleContratoModelo> detalles = detalleContratoServicio.obtenerDetalleContratoPorProveedorYEstadoPendiente(proveedor);
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/proveedor-aceptados/{proveedorId}")
    public ResponseEntity<List<DetalleContratoModelo>> obtenerDetalleContratoPorProveedorYEstadoAceptado(@PathVariable Integer proveedorId) {
        ProveedorModelo proveedor = new ProveedorModelo();
        proveedor.setIdProveedor(proveedorId);
        List<DetalleContratoModelo> detalles = detalleContratoServicio.obtenerDetalleContratoPorProveedorYEstadoAceptado(proveedor);
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<DetalleContratoModelo>> obtenerDetalleContratoPorCliente(@PathVariable Integer clienteId) {
        Optional<ClienteModelo> cliente = clienteServicio.encontrarClientePorId(clienteId);
        List<DetalleContratoModelo> detalles = detalleContratoServicio.obtenerDetalleContratoPorCliente(cliente);
        return ResponseEntity.ok(detalles);
    }
    
}