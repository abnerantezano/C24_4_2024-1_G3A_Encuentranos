import axios from 'axios';

class DetalleContratoService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/detalle-contrato';
    }

    getProveedores(idcliente) {
        return axios.get(`${this.baseUrl}/cliente/${idcliente}`, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener cliente: ', error);
                throw error;
            });
    }

    getClientes(idProveedor) {
        return axios.get(`${this.baseUrl}/proveedor/${idProveedor}`, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener cliente: ', error);
                throw error;
            });
    }
}

const DetalleContratoServiceInstance = new DetalleContratoService();
export default DetalleContratoServiceInstance;
