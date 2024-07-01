package com.proyecto.encuentranos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
@Service
public class ProveedorServicio {

	private final IProveedorRepositorio proveedorRepositorio;

	@Autowired
	public ProveedorServicio(IProveedorRepositorio proveedorRepositorio) {
		this.proveedorRepositorio = proveedorRepositorio;
	}

	//CRUD
    
    //CREATE
	public ProveedorModelo guardarProveedor(ProveedorModelo proveedor) {
		return proveedorRepositorio.save(proveedor);
	}
	
	//READ
	public List<ProveedorModelo> obtenerProveedores(){
		return proveedorRepositorio.findAll();
	}
	
	//UPDATE
	public ProveedorModelo actualizarProveedor(Integer id, ProveedorModelo proveedorActualizado) {
		Optional<ProveedorModelo> proveedorExistenteOptional = proveedorRepositorio.findById(id);
		if (proveedorExistenteOptional.isPresent()) {
			ProveedorModelo proveedorExistente = proveedorExistenteOptional.get();

			if (proveedorActualizado.getNombre() != null) {
				proveedorExistente.setNombre(proveedorActualizado.getNombre());
			}
			if (proveedorActualizado.getApellidoPaterno() != null) {
				proveedorExistente.setApellidoPaterno(proveedorActualizado.getApellidoPaterno());
			}
			if (proveedorActualizado.getApellidoMaterno() != null) {
				proveedorExistente.setApellidoMaterno(proveedorActualizado.getApellidoMaterno());
			}
			if (proveedorActualizado.getDni() != null) {
				proveedorExistente.setDni(proveedorActualizado.getDni());
			}
			if (proveedorActualizado.getCelular() != null) {
				proveedorExistente.setCelular(proveedorActualizado.getCelular());
			}
			if (proveedorActualizado.getSexo() != null) {
				proveedorExistente.setSexo(proveedorActualizado.getSexo());
			}
			if (proveedorActualizado.getFechaNacimiento() != null) {
				proveedorExistente.setFechaNacimiento(proveedorActualizado.getFechaNacimiento());
			}
			if (proveedorActualizado.getDescripcion() != null) {
				proveedorExistente.setDescripcion(proveedorActualizado.getDescripcion());
			}
			if (proveedorActualizado.getIdDistrito() != null) {
				proveedorExistente.setIdDistrito(proveedorActualizado.getIdDistrito());
			}

			proveedorExistente = proveedorRepositorio.save(proveedorExistente);
			return proveedorExistente;
		}
		return null;
	}

    //----------------------------------------

	//BUSCAR UN PROVEEDOR POR SU ID
	public Optional<ProveedorModelo> encontrarProveedorPorId(Integer id){
		return proveedorRepositorio.findById(id);
	}

	//BUSCAR UN PROVEEDOR POR SU CORREO
    public Optional<ProveedorModelo> buscarProveedorPorCorreo(String correo) {
        return proveedorRepositorio.findByIdUsuarioCorreo(correo);
    }
	
	//BUSCAR UN PROVEEDOR POR EL ID DEL USUARIO
	public Optional<ProveedorModelo> buscarProveedorPorUsuarioId(Integer usuarioId) {
	    return proveedorRepositorio.findByIdUsuarioIdUsuario(usuarioId);
	}
	
}
