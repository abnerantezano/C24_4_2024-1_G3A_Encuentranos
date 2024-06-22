import axios from "axios";

class TipoUsuarioService {

    constructor() {
        this.baseUrl = 'http://localhost:4000/tipo-usuario';
    }

    getAll() {
        return axios.get(this.baseUrl + '/listar',{withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener tipos de usuario: ', error);
                throw error;
            });
    }
    
}

export default new TipoUsuarioService();