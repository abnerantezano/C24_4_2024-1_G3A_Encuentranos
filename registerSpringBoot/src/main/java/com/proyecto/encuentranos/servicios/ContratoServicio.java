package com.proyecto.encuentranos.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.encuentranos.modelos.*;
import com.proyecto.encuentranos.repositorios.IContratoRepositorio;

import java.util.ArrayList;
import java.util.Date;

@Service
public class ContratoServicio {

    @Autowired
    private IContratoRepositorio contratoRepositorio;

    //SE CREAR LA PRIMERA PARTE DEL CONTRATO DONDE EL CLIENTE ENVIA SU SOLICITUD
    public ContratoModelo crearContrato(ContratoModelo contrato) {
        return contratoRepositorio.save(contrato);
    }
    
    //OBTENER TODOS LOS CONTRATOS QUE HAY
    public ArrayList<ContratoModelo> obtenerContratos(){
        return (ArrayList<ContratoModelo>) contratoRepositorio.findAll();
    }
    
    //SE CAMBIA EL MODO DEL CONTRATO A ACTIVO
    public ContratoModelo aceptarContratoProveedor(ContratoModelo contrato) {
        // CARGAR EL CONTRATO DESDE LA BASE DE DATOS
        ContratoModelo contratoExistente = contratoRepositorio.findById(contrato.getIdContrato()).orElseThrow(() -> new RuntimeException("Contrato no encontrado"));

        // ACTUALIZAR EL CAMPO ACTIVO
        contratoExistente.setEstado("Aceptado");

        // GUARDAR EL CONTRATO ESTA VEZ ACTIVO
        return contratoRepositorio.save(contratoExistente);
    }
    
    //SE CAMBIA EL MODO DEL CONTRATO A CANCELADO
    public ContratoModelo denegarContratoProveedor(ContratoModelo contrato) {
        // CARGAR EL CONTRATO DESDE LA BASE DE DATOS
        ContratoModelo contratoExistente = contratoRepositorio.findById(contrato.getIdContrato()).orElseThrow(() -> new RuntimeException("Contrato no encontrado"));
        
        // ACTUALIZAR EL CAMPO CANCELADO
        contratoExistente.setEstado("Cancelado");

        // GUARDAR EL CONTRATO ESTA VEZ CANCELADO
        return contratoRepositorio.save(contratoExistente);
    }
    
    // FINALIZAR LOS CONTRATOS
    public void finalizarContratosAutomaticamente() {
        //OBTENER LA FECHA ACTUAL
        Date fechaActual = new Date();

        // OBTENER TODOS LOS CONTRATOS
        ArrayList<ContratoModelo> contratos = obtenerContratos();

        // BUSCAR Y FINALIZAR LOS CONTRATOS VENCIDOS
        for (ContratoModelo contrato : contratos) {
            // VERIFICAR SI LA FECHA ACTUAL ES MAYOT A LA FECHA FIN DEL CONTRATO
            if (fechaActual.after(contrato.getFechaFin())) {
                // ACTUALIZAR A FINALIZADO AUTOMATICAMENTE
                contrato.setEstado("Finalizado");
                contratoRepositorio.save(contrato);
            }
        }
    }
}
