package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.repositorios.ITipoUsuarioRepositorio;

@Service
public class TipoUsuarioServicio {

    @Autowired
    ITipoUsuarioRepositorio tipoUsuarioRepositorio;
    
    public ArrayList<TipoUsuarioModelo> encontrarTipoUsuarios(){
    	return (ArrayList<TipoUsuarioModelo>)tipoUsuarioRepositorio.findAll();
    }
}
