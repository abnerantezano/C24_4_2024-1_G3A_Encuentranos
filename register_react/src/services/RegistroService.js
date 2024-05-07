import axios from "axios";

class RegistroService {
    baseUrl = 'http://localhost:4000/registro';

    postRegistrar(datos) {
        return axios.post(this.baseUrl + '/agregar',datos,{withCredentials: true})
            .then(res => res.data); 
    }
    
}

export default new RegistroService();