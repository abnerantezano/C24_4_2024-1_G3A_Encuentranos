package com.proyecto.encuentranos.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

<<<<<<< Updated upstream
@CrossOrigin(origins = "http://localhost:3000")
=======
@CrossOrigin(origins = "http://localhost:3000/")
>>>>>>> Stashed changes
@Controller
public class PublicController {
	@GetMapping("/public/messages")
	public String publicMessages(Model model) {
		model.addAttribute("body", "nobody");
		return "response";
	}
}
