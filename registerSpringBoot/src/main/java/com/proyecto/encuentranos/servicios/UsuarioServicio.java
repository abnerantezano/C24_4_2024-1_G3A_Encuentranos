package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.repositorios.IClienteRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
import com.proyecto.encuentranos.repositorios.IUsuarioRepositorio;

@Service
public class UsuarioServicio {

	@Autowired
	IUsuarioRepositorio usuarioRepositorio;
	
    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Autowired
    private IProveedorRepositorio proveedorRepositorio;
    
    public ArrayList<UsuarioModelo> obtenerUsuarios(){
    	return (ArrayList<UsuarioModelo>)usuarioRepositorio.findAll();
    }
	public UsuarioModelo guardarUsuario(UsuarioModelo usuario) {
		return usuarioRepositorio.save(usuario);
	}
	
    public Optional<UsuarioModelo> buscarUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }
    
    public boolean existsByEmail(String correo) {
        return usuarioRepositorio.existsByCorreo(correo);
    }

    public boolean existsInClienteOrProveedor(String correo) {
        return clienteRepositorio.existsByIdUsuarioCorreo(correo) || proveedorRepositorio.existsByIdUsuarioCorreo(correo);
    }
}
