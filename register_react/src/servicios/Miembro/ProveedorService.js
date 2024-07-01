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

    putProveedor(idProveedor, datos) {
        return axios.put(this.baseUrl + `/actualizar/${idProveedor}`, datos, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al actulizar datos del proveedor: ', error);
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

    getProveedorDetalle(idProveedor) {
        return axios.get(this.baseUrl + `/buscar/${idProveedor}` , {withCredentials:true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener proveedor:', error);
                throw error;
            });
    }
}

const ProveedorServiceInstance = new ProveedorService();

export default ProveedorServiceInstance;
