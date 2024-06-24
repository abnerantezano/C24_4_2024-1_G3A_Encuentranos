import axios from 'axios';

class ProveedorService {

    constructor() {
        this.baseUrl = 'http://localhost:4000/proveedor';
    }
    
    addProveedor(datos) {
        return axios.post(this.baseUrl + '/agregar', datos, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al agregar proveedor:', error);
                throw error;
            });
    }

    getListaProveedor() {
        return axios.get(this.baseUrl + '/buscar', { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener proveedores:', error);
                throw error;
            });
    }
}

const ProveedorServiceInstance = new ProveedorService();

export default ProveedorServiceInstance;
