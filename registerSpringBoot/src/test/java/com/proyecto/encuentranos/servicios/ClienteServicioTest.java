package com.proyecto.encuentranos.servicios;

import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.repositorios.IClienteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServicioTest {

    private ClienteServicio clienteServicio;

    @Mock
    private IClienteRepositorio clienteRepositorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks
        clienteServicio = new ClienteServicio(clienteRepositorio); // Crear el servicio con el mock
    }

    @Test
    void buscarClientePorCorreo() {
    }
}
