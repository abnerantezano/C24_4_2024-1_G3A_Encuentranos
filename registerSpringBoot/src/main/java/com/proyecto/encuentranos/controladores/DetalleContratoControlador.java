package com.proyecto.encuentranos.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.*;
import com.proyecto.encuentranos.servicios.DetalleContratoServicio;

@RestController
@RequestMapping("/detalle-contrato")
public class DetalleContratoControlador {
    
    private final DetalleContratoServicio detalleContratoServicio;

    @Autowired
    public DetalleContratoControlador(DetalleContratoServicio detalleContratoServicio) {
        this.detalleContratoServicio = detalleContratoServicio;
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
    
}