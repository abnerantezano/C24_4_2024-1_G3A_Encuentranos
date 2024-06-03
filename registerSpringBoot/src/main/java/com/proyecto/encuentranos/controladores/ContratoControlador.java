package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyecto.encuentranos.modelos.ContratoModelo;
import com.proyecto.encuentranos.servicios.ContratoServicio;

@RestController
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicio contratoServicio;

    // Crear un nuevo contrato
    @PostMapping("/crear")
    public ResponseEntity<ContratoModelo> crearContrato(@RequestBody ContratoModelo contrato) {
        ContratoModelo nuevoContrato = contratoServicio.crearContrato(contrato);
        return new ResponseEntity<>(nuevoContrato, HttpStatus.CREATED);
    }
    
    // Listar todos los contratos
    @GetMapping("/listar")
    public ResponseEntity<ArrayList<ContratoModelo>> obtenerContratos() {
        ArrayList<ContratoModelo> contratos = contratoServicio.obtenerContratos();
        return new ResponseEntity<>(contratos, HttpStatus.OK);
    }
    
    // Aceptar un contrato por el proveedor
    @PutMapping("/aceptar-proveedor")
    public ResponseEntity<ContratoModelo> aceptarContratoProveedor(@RequestBody ContratoModelo contrato) {
        ContratoModelo contratoAceptado = contratoServicio.aceptarContratoProveedor(contrato);
        if (contratoAceptado != null) {
            return new ResponseEntity<>(contratoAceptado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Denegar un contrato por el proveedor
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
