import axios from "axios";

class UsuarioService {
    baseUrl = 'http://localhost:4000/usuario';

    getAll() {
        return axios.get(this.baseUrl + '/listar')
            .then(res => res.data); 
    }

    addUser(nuevoUsuario) { 
        return axios.post(this.baseUrl + '/agregar', nuevoUsuario)
            .then(res => res.data);
    }
    
}

export default new UsuarioService();