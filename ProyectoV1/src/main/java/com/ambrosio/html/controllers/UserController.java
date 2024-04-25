package com.ambrosio.html.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ambrosio.html.models.UserModel;
import com.ambrosio.html.services.UserServices;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServices userService;
	
	@GetMapping
	public ArrayList<UserModel> getUsers(){
		return this.userService.getUsers();
	}
	
	@PostMapping
	public UserModel saveUser(@RequestBody UserModel user) {
		return this.userService.saveUser(user);
	}
	
	@GetMapping(path = "/{id}")
	public Optional<UserModel> getUserById(@PathVariable("id") Long id){
		return this.userService.getByID(id);
	}
	
	@PutMapping(path = "/{id}")
	public UserModel updateUserById(@RequestBody UserModel request, @PathVariable("id") Long id) {
		return this.userService.updateById(request, id);
	}
	
	@DeleteMapping(path = "/{id}")
	public String deleteById(@PathVariable("id") Long id) {
		Boolean ok = this.userService.deleteUser(id);
		if(ok) {
			return "User with id " + id + " deleted";
		}else {
			return "Error, we have a problem and can't delet user from: " + id;
		}
	}
}