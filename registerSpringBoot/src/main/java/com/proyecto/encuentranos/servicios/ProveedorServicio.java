package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;

@Service
public class ProveedorServicio {
	@Autowired
	IProveedorRepositorio proveedorRepositorio;
	
	public ArrayList<ProveedorModelo> obtenerProveedores(){
		return (ArrayList<ProveedorModelo>)proveedorRepositorio.findAll();
	}
	
	public ProveedorModelo guardarProveedor(ProveedorModelo proveedor) {
		return proveedorRepositorio.save(proveedor);
	}
	
	public ProveedorModelo actualizarProveedor(Integer id, ProveedorModelo proovedorActualizado) {
		ProveedorModelo proveedorExistente = proveedorRepositorio.findById(id).orElse(null);
        if (proveedorExistente != null) {
            // Actualizar datos del usuario
        	proveedorExistente.setNombre(proovedorActualizado.getNombre());
        	proveedorExistente.setApellidoPaterno(proovedorActualizado.getApellidoPaterno());
        	proveedorExistente.setApellidoMaterno(proovedorActualizado.getApellidoMaterno());
        	proveedorExistente.setSexo(proovedorActualizado.getSexo());
        	proveedorExistente.setFechaNacimiento(proovedorActualizado.getFechaNacimiento());
        	proveedorExistente.setDni(proovedorActualizado.getDni());
        	proveedorExistente.setCelular(proovedorActualizado.getCelular());
        	proveedorExistente.setDistrito(proovedorActualizado.getDistrito());

            // Actualizar correo y contraseña del usuario
        	proveedorExistente.getIdUsuario().setCorreo(proovedorActualizado.getIdUsuario().getCorreo());
        	proveedorExistente.getIdUsuario().setContrasena(proovedorActualizado.getIdUsuario().getContrasena());

            // Guardar el proveedor actualizado
        	proveedorExistente = proveedorRepositorio.save(proveedorExistente);
        }
        return proveedorExistente;
	}
	
	public Optional<ProveedorModelo> encontrarProveedorPorId(Integer id){
		return proveedorRepositorio.findById(id);
	}
	
	
}
