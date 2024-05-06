import axios from "axios";

class TipoUsuarioService {
    baseUrl = 'http://localhost:4000/tipo-usuario';

    getAll() {
        return axios.get(this.baseUrl + '/listar',{withCredentials: true})
            .then(res => res.data); 
    }
    
}

export default new TipoUsuarioService();