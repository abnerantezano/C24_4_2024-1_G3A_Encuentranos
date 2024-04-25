package com.ambrosio.html.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ambrosio.html.models.UserModel;
import com.ambrosio.html.models.DireccionModel;
import com.ambrosio.html.repositories.IUserRepository;

@Service
public class UserServices {
    
    @Autowired
    IUserRepository userRepository;
    
    public ArrayList<UserModel> getUsers(){
        return (ArrayList<UserModel>)userRepository.findAll();
    }
    
    public UserModel saveUser(UserModel user) {
        if (user.getDirecciones() != null && !user.getDirecciones().isEmpty()) {
            for (DireccionModel direccion : user.getDirecciones()) {
                direccion.setUsuario(user);
            }
        }
        return userRepository.save(user);
    }
    
    public Optional<UserModel> getByID(Long id){
        return userRepository.findById(id);
    }
    
    public UserModel updateById(UserModel request, Long id){
        UserModel user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setTelefono(request.getTelefono());
            user.setDni(request.getDni());
            user.setCorreoElectronico(request.getCorreoElectronico());
            user.setContrasena(request.getContrasena());
            user.setTipo(request.getTipo());


            if (request.getDirecciones() != null && !request.getDirecciones().isEmpty()) {
                DireccionModel direccion = user.getDirecciones().get(0); 

                DireccionModel newDireccion = request.getDirecciones().get(0); 

                direccion.setCalle(newDireccion.getCalle());
                direccion.setNumero(newDireccion.getNumero());
                direccion.setCiudad(newDireccion.getCiudad());
                direccion.setRegion(newDireccion.getRegion());
            }

            return userRepository.save(user);
        } else {
            return null;
        }
    }




    
    public Boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
