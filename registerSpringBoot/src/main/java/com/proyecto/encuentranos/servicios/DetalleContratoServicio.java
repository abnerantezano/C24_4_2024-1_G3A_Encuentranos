package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.*;
import com.proyecto.encuentranos.repositorios.IDetalleContratoRepositorio;

@Service
public class DetalleContratoServicio {
    
    @Autowired
    private IDetalleContratoRepositorio detalleContratoRepositorio;
    
    //OBTENER TODOS LOS DETALLES CONTRATOS QUE HAY
    public ArrayList<DetalleContratoModelo> obtenerDetalleContratos(){
        return (ArrayList<DetalleContratoModelo>) detalleContratoRepositorio.findAll();
    }
    
    // OBTENER DETALLES CONTRATOS POR PROVEEDOR
    public List<DetalleContratoModelo> obtenerDetalleContratoPorProveedor(ProveedorModelo proveedor){
        return detalleContratoRepositorio.findByIdProveedor(proveedor);
    }
    
    // OBTENER DETALLES CONTRATOS POR CLIENTE
    public List<DetalleContratoModelo> obtenerDetalleContratoPorCliente(ContratoModelo contrato){
        return detalleContratoRepositorio.findByIdContratoIdCliente(contrato);
    }
    
}
