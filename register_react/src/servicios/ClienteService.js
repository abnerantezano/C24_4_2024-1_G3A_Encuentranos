import axios from 'axios';

export class ClienteService {
    baseUrl = 'http://localhost:4000/cliente';

    addCliente(datos) {
        return axios.post(this.baseUrl + '/agregar',datos,{withCredentials: true})
            .then(res => res.data); 
    }
}

export default new ClienteService();