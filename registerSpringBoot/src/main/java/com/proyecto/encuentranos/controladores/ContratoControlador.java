package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.proyecto.encuentranos.modelos.*;
import com.proyecto.encuentranos.servicios.ContratoServicio;

@RestController
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicio contratoServicio;

    @PostMapping("/crear")
    public ContratoModelo crearContrato(@RequestBody ContratoModelo contrato) {
        return contratoServicio.crearContrato(contrato);
    }
    
    @GetMapping("/listar")
    public ArrayList<ContratoModelo> obtenerContratos(){
    	return contratoServicio.obtenerContratos();
    }
    
    @PutMapping("/aceptar-proveedor")
    public ContratoModelo aceptarContratoProveedor(@RequestBody ContratoModelo contrato) {
    	return contratoServicio.aceptarContratoProveedor(contrato);
    }
    
    @PutMapping("/denegar-proveedor")
    public ContratoModelo denegarContratoProveedor(@RequestBody ContratoModelo contrato) {
    	return contratoServicio.denegarContratoProveedor(contrato);
    }

}
