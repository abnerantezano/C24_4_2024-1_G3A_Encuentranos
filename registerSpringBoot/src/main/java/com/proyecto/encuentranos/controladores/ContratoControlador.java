package com.proyecto.encuentranos.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyecto.encuentranos.modelos.ContratoModelo;
import com.proyecto.encuentranos.modelos.DetalleContratoModelo;
import com.proyecto.encuentranos.servicios.ContratoServicio;

@RestController
@RequestMapping("/contrato")
public class ContratoControlador {

    private final ContratoServicio contratoServicio;

    @Autowired
    public ContratoControlador(ContratoServicio contratoServicio) {
        this.contratoServicio = contratoServicio;
    }

    @PostMapping("/crear")
    public ResponseEntity<ContratoModelo> crearContrato(@RequestBody ContratoModelo contrato) {
        ContratoModelo nuevoContrato = contratoServicio.crearContrato(contrato);
        return new ResponseEntity<>(nuevoContrato, HttpStatus.CREATED);
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<ContratoModelo>> obtenerContratos() {
        List<ContratoModelo> contratos = contratoServicio.obtenerContratos();
        return new ResponseEntity<>(contratos, HttpStatus.OK);
    }
    
    @GetMapping("/listar/cliente/{idCliente}")
    public ResponseEntity<List<ContratoModelo>> obtenerContratosPorCliente(@PathVariable Integer idCliente) {
        List<ContratoModelo> contratos = contratoServicio.obtenerContratoPorIdCliente(idCliente);
        return new ResponseEntity<>(contratos, HttpStatus.OK);
    }
    
    @GetMapping("/listar/proveedor/{idProveedor}")
    public ResponseEntity<List<DetalleContratoModelo>> obtenerContratoPorIdProveedor(@PathVariable Integer idProveedor) {
        List<DetalleContratoModelo> contratos = contratoServicio.obtenerContratoPorIdProveedor(idProveedor);
        return new ResponseEntity<>(contratos, HttpStatus.OK);
    }
    
    @PutMapping("/aceptar-proveedor")
    public ResponseEntity<ContratoModelo> aceptarContratoProveedor(@RequestBody ContratoModelo contrato) {
        ContratoModelo contratoAceptado = contratoServicio.aceptarContratoProveedor(contrato);
        if (contratoAceptado != null) {
            return new ResponseEntity<>(contratoAceptado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/denegar-proveedor")
    public ResponseEntity<ContratoModelo> denegarContratoProveedor(@RequestBody ContratoModelo contrato) {
        ContratoModelo contratoDenegado = contratoServicio.denegarContratoProveedor(contrato);
        if (contratoDenegado != null) {
            return new ResponseEntity<>(contratoDenegado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
