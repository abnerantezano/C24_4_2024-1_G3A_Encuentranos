package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.encuentranos.modelos.ServicioPrestadorModelo;
import com.proyecto.encuentranos.servicios.ServicioPrestadorServicio;
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/servicio-prestador")
public class ServicioPrestadorControlador {
	
	@Autowired
	ServicioPrestadorServicio servicioPrestadorServicio;
	
	@GetMapping("/listar")
	public ArrayList<ServicioPrestadorModelo> obtenerServiciosPrestadores(){
		return this.servicioPrestadorServicio.obtenerServiciosPrestadores();
	}
	
	@GetMapping("/buscar/{id}")
	public Optional <ServicioPrestadorModelo> obtenerServicioPrestadorPorId(@PathVariable Long id){
		return this.servicioPrestadorServicio.obtenerServicioPrestadorPorId(id);
	}
	
    @PostMapping("/agregar")
    public List<ServicioPrestadorModelo> agregarServicioPrestador(@RequestBody List<ServicioPrestadorModelo> serviciosPrestador) {
        List<ServicioPrestadorModelo> serviciosAgregados = new ArrayList<>();
        for (ServicioPrestadorModelo servicioPrestador : serviciosPrestador) {
            try {
                serviciosAgregados.add(servicioPrestadorServicio.agregarServicioPrestador(servicioPrestador));
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al agregar servicio prestador: " + e.getMessage());
            }
        }
        return serviciosAgregados;
    }
	
	@PutMapping(path="/actualizar/{id}")
	public ServicioPrestadorModelo actualizarServicioPrestador(@RequestBody ServicioPrestadorModelo servicioPrestador, @PathVariable Long id) {
		return this.servicioPrestadorServicio.actualizarServicioPrestador(id, servicioPrestador);
	}

}
