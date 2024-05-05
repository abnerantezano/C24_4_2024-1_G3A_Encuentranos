package com.proyecto.encuentranos.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home () {
		return "inicio";
	}
}
