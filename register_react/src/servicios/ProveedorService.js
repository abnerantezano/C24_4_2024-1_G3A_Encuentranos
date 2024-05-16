import axios from 'axios';

export class ProveedorService {
    baseUrl = 'http://localhost:4000/proveedor';
    
    addProveedor(datos) {
        return axios.post(this.baseUrl + '/agregar', datos, { withCredentials: true })
            .then(res => res.data)
    }
}

export default new ProveedorService();