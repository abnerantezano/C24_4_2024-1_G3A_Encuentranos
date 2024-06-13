package com.proyecto.encuentranos.servicios;

import com.proyecto.encuentranos.exepciones.ContratoNoEncontradoException;
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

    //SE CREAR LA PRIMERA PARTE DEL CONTRATO DONDE EL CLIENTE ENVIA SU SOLICITUD
    public ContratoModelo crearContrato(ContratoModelo contrato) {
        return contratoRepositorio.save(contrato);
    }
    
    //OBTENER TODOS LOS CONTRATOS QUE HAY
    public List<ContratoModelo> obtenerContratos(){
        return contratoRepositorio.findAll();
    }
    
    //OBTENER CONTRATO POR EL ID DEL CLIENTE
    public List<ContratoModelo> obtenerContratoPorIdCliente(Integer idCliente) {
        // Buscar el contrato en el repositorio por el ID del cliente
        List<ContratoModelo> contrato = contratoRepositorio.findByIdClienteIdCliente(idCliente);

        // Si el contrato no se encuentra, arrojar una excepción específica
        if (contrato == null || contrato.isEmpty()) {
            throw new ContratoNoEncontradoException("Contrato no encontrado para el cliente con ID: " + idCliente);
        }

        // Devolver el contrato encontrado
        return contrato;
    }

    
  //OBTENER CONTRATO POR EL ID DEL PROVEEDOR
    public List<DetalleContratoModelo> obtenerContratoPorIdProveedor(Integer idProveedor) {
        // Buscar el contrato en el repositorio por el ID del proveedor
        List<DetalleContratoModelo> contrato = detalleContratoRepositorio.findByIdProveedorIdProveedor(idProveedor);

        // Si el contrato no se encuentra, arrojar una excepción
        if (contrato == null || contrato.isEmpty()) {
            throw new ContratoNoEncontradoException("Contrato no encontrado para el cliente con ID: " + idProveedor);
        }

        // Devolver el contrato encontrado
        return contrato;
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
        List<ContratoModelo> contratos = obtenerContratos();

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
