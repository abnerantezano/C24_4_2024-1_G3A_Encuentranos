import axios from 'axios';

class ContratoService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/contrato';
    }

    getProveedores(idProveedor) {
        return axios.get(`${this.baseUrl}/listar/proveedor/${idProveedor}`, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener cliente: ', error);
                throw error;
            });
    }

    getClientes(idCliente) {
        return axios.get(`${this.baseUrl}/listar/proveedor/${idCliente}`, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener cliente: ', error);
                throw error;
            });
    }
}

const ContratoServiceInstance = new ContratoService();
export default ContratoServiceInstance;
