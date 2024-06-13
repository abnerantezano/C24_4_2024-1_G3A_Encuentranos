package com.proyecto.encuentranos.exepciones;

public class ContratoNoEncontradoException extends RuntimeException {
    public ContratoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
