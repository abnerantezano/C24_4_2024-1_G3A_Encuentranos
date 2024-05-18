package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.repositorios.ITipoUsuarioRepositorio;
//ESTAMOS CREANDO EL SERVICIO PARA TipoUsuarioServicio
@Service
public class TipoUsuarioServicio {

	//INSTANCIAR LAS CLASES QUE USAREMOS

    @Autowired
    ITipoUsuarioRepositorio tipoUsuarioRepositorio;
    
    //LISTAR
    public ArrayList<TipoUsuarioModelo> encontrarTipoUsuarios(){
    	return (ArrayList<TipoUsuarioModelo>)tipoUsuarioRepositorio.findAll();
    }
}
