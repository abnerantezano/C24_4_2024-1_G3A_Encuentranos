package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.DistritoModelo;
import com.proyecto.encuentranos.repositorios.IDistritoRepositorio;
//ESTAMOS CREANDO EL SERVICIO PARA Distrito
@Service
public class DistritoServicio {
	
	//INSTANCIAR LAS CLASES QUE USAREMOS
	
	@Autowired
	IDistritoRepositorio distritoRepositorio;
	
	//LISTAR DISTRITOS
	public ArrayList<DistritoModelo> obtenerDistritos(){
		return (ArrayList<DistritoModelo>)distritoRepositorio.findAll();
	}
}
