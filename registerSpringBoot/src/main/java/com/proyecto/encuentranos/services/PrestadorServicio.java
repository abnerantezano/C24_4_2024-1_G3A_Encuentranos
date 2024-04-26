package com.proyecto.encuentranos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.models.PrestadorModel;
import com.proyecto.encuentranos.models.UsuarioModel;
import com.proyecto.encuentranos.repositories.IPrestadorRepositorio;

@Service
public class PrestadorServicio {

    @Autowired
    IPrestadorRepositorio prestadorRepositorio;

    @Autowired
    UsuarioServicio usuarioServicio;

    public List<PrestadorModel> obtenerPrestadoresConUsuario() {
        List<PrestadorModel> prestadores = prestadorRepositorio.findAll();
        for (PrestadorModel prestador : prestadores) {
            UsuarioModel usuario = usuarioServicio.obtenerUsuarioPorId(prestador.getUsuario().getId()).orElse(null);
            prestador.setUsuario(usuario);
        }
        return prestadores;
    }

    public PrestadorModel guardarPrestador(PrestadorModel prestador) {
        return prestadorRepositorio.save(prestador);
    }

    public PrestadorModel obtenerPrestadorConUsuarioPorId(Long id) {
        Optional<PrestadorModel> optionalPrestador = prestadorRepositorio.findById(id);
        if (optionalPrestador.isPresent()) {
            PrestadorModel prestador = optionalPrestador.get();
            UsuarioModel usuario = usuarioServicio.obtenerUsuarioPorId(prestador.getUsuario().getId()).orElse(null);
            prestador.setUsuario(usuario);
            return prestador;
        } else {
            return null;
        }
    }
}

