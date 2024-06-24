import axios from 'axios';

class ClienteService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/cliente';
    }

    addCliente(datos) {
        return axios.post(`${this.baseUrl}/agregar`, datos, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al agregar cliente: ', error);
                throw error;
            });
    }

    getCliente(idcliente) {
        return axios.get(`${this.baseUrl}/buscar/${idcliente}`, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener cliente: ', error);
                throw error;
            });
    }
}

const clienteServiceInstance = new ClienteService();
export default clienteServiceInstance;
