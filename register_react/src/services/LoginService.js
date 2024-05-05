import axios from "axios";

class LoginService {
    baseUrl = 'http://localhost:4000/login';

    auth2() {
        return axios.get(this.baseUrl + '/messages')
            .then(res => res.data); 
    }

    addUser(nuevoUsuario) { 
        return axios.post(this.baseUrl + '/agregar-usuario', nuevoUsuario)
            .then(res => res.data);
    }
    
}

export default new LoginService();