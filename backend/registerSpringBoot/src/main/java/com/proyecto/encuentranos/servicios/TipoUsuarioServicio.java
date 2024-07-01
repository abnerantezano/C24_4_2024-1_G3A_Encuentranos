package com.proyecto.encuentranos.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
import com.proyecto.encuentranos.repositorios.ITipoUsuarioRepositorio;
//ESTAMOS CREANDO EL SERVICIO PARA TipoUsuarioServicio
@Service
public class TipoUsuarioServicio {

    private final ITipoUsuarioRepositorio tipoUsuarioRepositorio;

    @Autowired
    public TipoUsuarioServicio(ITipoUsuarioRepositorio tipoUsuarioRepositorio) {
        this.tipoUsuarioRepositorio = tipoUsuarioRepositorio;
    }

    //LISTAR
    public List<TipoUsuarioModelo> encontrarTipoUsuarios(){
    	return tipoUsuarioRepositorio.findAll();
    }
}
