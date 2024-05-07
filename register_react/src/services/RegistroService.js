import axios from "axios";

class RegistroService {
    baseUrl = 'http://localhost:4000/registro';

    postRegistrar() {
        return axios.post(this.baseUrl + '/agregar',{withCredentials: true})
            .then(res => res.data); 
    }
    
}

export default new RegistroService();