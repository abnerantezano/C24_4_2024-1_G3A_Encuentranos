import axios from "axios";

class IniciarSesionService {
    baseUrl = 'http://localhost:4000/usuario';

    getUsuario() {
        return axios.get(this.baseUrl + '/token')
            .then(res => res.data); 
    }

    addUser(nuevoUsuario) { 

        return axios.post(this.baseUrl + '/agregar-usuario', nuevoUsuario)

            .then(res => res.data);
    }
    
}

export default new IniciarSesionService();

