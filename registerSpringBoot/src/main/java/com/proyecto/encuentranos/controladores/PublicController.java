package com.proyecto.encuentranos.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/public")
@Controller
public class PublicController {
	@GetMapping("/messages")
	public String publicMessages(Model model) {
		model.addAttribute("body", "nobody");
		return "response";
	}
	
	@GetMapping("/inicio")
	public String home () {
		return "inicio";
	}
}
