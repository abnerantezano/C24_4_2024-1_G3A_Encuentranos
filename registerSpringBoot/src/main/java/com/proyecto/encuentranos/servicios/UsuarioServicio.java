package com.proyecto.encuentranos.servicios;

import java.util.List;
import java.util.Optional;

import com.proyecto.encuentranos.auth.config.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.repositorios.IClienteRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
import com.proyecto.encuentranos.repositorios.IUsuarioRepositorio;
@Service
public class UsuarioServicio {

    private final PasswordConfig passwordEncoder;

	private final IUsuarioRepositorio usuarioRepositorio;
	
    private final IClienteRepositorio clienteRepositorio;

    private final IProveedorRepositorio proveedorRepositorio;

    @Autowired
    public UsuarioServicio(PasswordConfig passwordEncoder, IUsuarioRepositorio usuarioRepositorio, IClienteRepositorio clienteRepositorio, IProveedorRepositorio proveedorRepositorio) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepositorio = usuarioRepositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.proveedorRepositorio = proveedorRepositorio;
    }

    public UsuarioServicio(IUsuarioRepositorio usuarioRepositorio) {
        this.passwordEncoder = null;
        this.usuarioRepositorio = usuarioRepositorio;
        this.clienteRepositorio = null;
        this.proveedorRepositorio = null;
    }
    //CRUD

    //CREATE
    public UsuarioModelo guardarUsuario(UsuarioModelo usuario) {
        usuario.setContrasena(passwordEncoder.passwordEncoder().encode(usuario.getContrasena()));
        return usuarioRepositorio.save(usuario);
    }
    //READ
    public List<UsuarioModelo> obtenerUsuarios(){
    	return usuarioRepositorio.findAll();
    }
    
    //UPDATE
    public UsuarioModelo actualizarUsuario(Integer id, UsuarioModelo usuarioActualizado){
        UsuarioModelo usuarioExistente = usuarioRepositorio.findById(id).orElse(null);
        if (usuarioExistente != null) {
            if (usuarioActualizado.getCorreo() != null) {
                usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
            }
            if (usuarioActualizado.getContrasena() != null) {
                usuarioExistente.setContrasena((passwordEncoder.passwordEncoder().encode(usuarioActualizado.getContrasena())));
            }
            if (usuarioActualizado.getImagenUrl() != null) {
                usuarioExistente.setImagenUrl(usuarioActualizado.getImagenUrl());
            }

            usuarioExistente.setActivo(usuarioActualizado.isActivo());

            usuarioExistente = usuarioRepositorio.save(usuarioExistente);
        }
        return usuarioExistente;
    }

    //----------------------------------------
	
    //BUSCAR UN USUARIO POR SU CORREO
    public Optional<UsuarioModelo> buscarUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }
    
    //BUSCAR USUARIO POR SU ID
    public Optional<UsuarioModelo> buscarUsuarioPorId(int id){
    	return usuarioRepositorio.findById(id);
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
