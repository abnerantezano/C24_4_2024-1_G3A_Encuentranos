import axios from "axios";

<<<<<<< Updated upstream
class LoginService {
    baseUrl = 'http://localhost:4000/login';

    auth2() {
        return axios.get(this.baseUrl + '/messages')
=======
class UsuarioService {
    baseUrl = 'http://localhost:4000/login';

    getAll() {
        return axios.get(this.baseUrl + '/listar')
>>>>>>> Stashed changes
            .then(res => res.data); 
    }

    addUser(nuevoUsuario) { 
<<<<<<< Updated upstream
        return axios.post(this.baseUrl + '/agregar-usuario', nuevoUsuario)
=======
        return axios.post(this.baseUrl + '/agregar', nuevoUsuario)
>>>>>>> Stashed changes
            .then(res => res.data);
    }
    
}

<<<<<<< Updated upstream
export default new LoginService();
=======
export default new UsuarioService();
>>>>>>> Stashed changes
