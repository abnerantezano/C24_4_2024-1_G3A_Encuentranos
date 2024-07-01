package com.proyecto.encuentranos.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.DistritoModelo;
import com.proyecto.encuentranos.repositorios.IDistritoRepositorio;
@Service
public class DistritoServicio {
	
	private final IDistritoRepositorio distritoRepositorio;

	@Autowired
	public DistritoServicio(IDistritoRepositorio distritoRepositorio){
		this.distritoRepositorio = distritoRepositorio;
	}
	
	//LISTAR DISTRITOS
	public List<DistritoModelo> obtenerDistritos(){
		return distritoRepositorio.findAll();
	}
}
