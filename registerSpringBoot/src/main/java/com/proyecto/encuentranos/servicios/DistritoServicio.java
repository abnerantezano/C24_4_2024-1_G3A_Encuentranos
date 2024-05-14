package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.DistritoModelo;
import com.proyecto.encuentranos.repositorios.IDistritoRepositorio;

@Service
public class DistritoServicio {

	@Autowired
	IDistritoRepositorio distritoRepositorio;
	
	public ArrayList<DistritoModelo> obtenerDistritos(){
		return (ArrayList<DistritoModelo>)distritoRepositorio.findAll();
	}
}
