package com.proyecto.encuentranos.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.encuentranos.modelos.*;
import com.proyecto.encuentranos.repositorios.IContratoRepositorio;
import com.proyecto.encuentranos.repositorios.IDetalleContratoRepositorio;

import java.util.Date;
import java.util.List;

@Service
public class ContratoServicio {

    private final IContratoRepositorio contratoRepositorio;
    private final IDetalleContratoRepositorio detalleContratoRepositorio;

    @Autowired
    public ContratoServicio(IContratoRepositorio contratoRepositorio, IDetalleContratoRepositorio detalleContratoRepositorio) {
        this.contratoRepositorio = contratoRepositorio;
        this.detalleContratoRepositorio = detalleContratoRepositorio;
    }

    //CRUD

    //CREATE

    //SE CREAR LA PRIMERA PARTE DEL CONTRATO DONDE EL CLIENTE ENVIA SU SOLICITUD
    public ContratoModelo crearContrato(ContratoModelo contrato) {
        return contratoRepositorio.save(contrato);
    }

    //READ
    public List<ContratoModelo> obtenerContratos(){
        return contratoRepositorio.findAll();
    }

    //-------------------------------------------------------
    
    //OBTENER CONTRATO POR EL ID DEL CLIENTE
    public List<ContratoModelo> obtenerContratoPorIdCliente(Integer idCliente) {
        return contratoRepositorio.findByIdClienteIdCliente(idCliente);
    }

    
  //OBTENER CONTRATO POR EL ID DEL PROVEEDOR
    public List<DetalleContratoModelo> obtenerContratoPorIdProveedor(Integer idProveedor) {
        return detalleContratoRepositorio.findByIdProveedorIdProveedor(idProveedor);
    }

    //SE CAMBIA EL MODO DEL CONTRATO A ACTIVO CUANDO EL PROVEEDOR ACEPTE
    public ContratoModelo aceptarContratoProveedor(ContratoModelo contrato) {

        ContratoModelo contratoExistente = contratoRepositorio.findById(contrato.getIdContrato()).orElseThrow(() -> new RuntimeException("Contrato no encontrado"));

        contratoExistente.setEstado("Aceptado");

        return contratoRepositorio.save(contratoExistente);
    }
    
    //SE CAMBIA EL MODO DEL CONTRATO A CANCELADO SI EL PROVEEDOR CANCELA
    public ContratoModelo denegarContratoProveedor(ContratoModelo contrato) {
        ContratoModelo contratoExistente = contratoRepositorio.findById(contrato.getIdContrato()).orElseThrow(() -> new RuntimeException("Contrato no encontrado"));
        
        contratoExistente.setEstado("Cancelado");

        return contratoRepositorio.save(contratoExistente);
    }
    
    // FINALIZAR LOS CONTRATOS
    public void finalizarContratosAutomaticamente() {
        Date fechaActual = new Date();
        List<ContratoModelo> contratos = obtenerContratos();

        for (ContratoModelo contrato : contratos) {
            if (fechaActual.after(contrato.getFechaFin())) {
                contrato.setEstado("Finalizado");
                contratoRepositorio.save(contrato);
            }
        }
    }
}
