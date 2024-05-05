package com.proyecto.encuentranos.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public")
@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home () {
		return "redirect:http://localhost:3000/Formulario";
	}
}
