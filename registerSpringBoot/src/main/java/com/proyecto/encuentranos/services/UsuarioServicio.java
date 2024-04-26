package com.proyecto.encuentranos.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.models.DireccionModel;
import com.proyecto.encuentranos.models.UsuarioModel;
import com.proyecto.encuentranos.repositories.IUsuarioRepositorio;

@Service
public class UsuarioServicio {
	
	@Autowired
	IUsuarioRepositorio usuarioRepositorio;
	
	public ArrayList<UsuarioModel> obtenerUsuarios(){
		return (ArrayList<UsuarioModel>)usuarioRepositorio.findAll();
	}
	
	public UsuarioModel guardarUsuario(UsuarioModel usuario) {
		if(usuario.getDirecciones() != null && !usuario.getDirecciones().isEmpty()){
			for (DireccionModel direccion : usuario.getDirecciones()) {
				direccion.setUsuario(usuario);
			}
		}
		return usuarioRepositorio.save(usuario);
	}
	
	public Optional<UsuarioModel> obtenerUsuarioPorId(Long id){
		return usuarioRepositorio.findById(id);
	}
	

}
