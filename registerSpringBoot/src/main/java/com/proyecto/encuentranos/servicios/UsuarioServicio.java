package com.proyecto.encuentranos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.repositorios.IClienteRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
import com.proyecto.encuentranos.repositorios.IUsuarioRepositorio;
//ESTAMOS CREANDO EL SERVICIO PARA Usuario
@Service
public class UsuarioServicio {

	//INSTANCIAR LAS CLASES QUE USAREMOS
	private final IUsuarioRepositorio usuarioRepositorio;
	
    private final IClienteRepositorio clienteRepositorio;

    private final IProveedorRepositorio proveedorRepositorio;

    @Autowired
    public UsuarioServicio(IUsuarioRepositorio usuarioRepositorio, IClienteRepositorio clienteRepositorio, IProveedorRepositorio proveedorRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.proveedorRepositorio = proveedorRepositorio;
    }

    //CRUD
    
    //CREATE
	public UsuarioModelo guardarUsuario(UsuarioModelo usuario) {
		return usuarioRepositorio.save(usuario);
	}
	
    //READ
    public List<UsuarioModelo> obtenerUsuarios(){
    	return usuarioRepositorio.findAll();
    }
    
    //UPDATE
    
    //DELETE
    //----------------------------------------
	
    //BUSCAR UN USUARIO POR SU CORREO
    public Optional<UsuarioModelo> buscarUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }
    
    //VERIFICAR SI EL CORREO EXISTE
    public boolean existsByEmail(String correo) {
        return usuarioRepositorio.existsByCorreo(correo);
    }

    //VERIFICAR SI EXISTE UN CLIENTE O PROVEEDOR CON EL CORREO EXISTENTE
    public boolean existsInClienteOrProveedor(String correo) {
        return clienteRepositorio.existsByIdUsuarioCorreo(correo) || proveedorRepositorio.existsByIdUsuarioCorreo(correo);
    }
}
