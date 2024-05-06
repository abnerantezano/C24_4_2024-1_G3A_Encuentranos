import axios from 'axios';

class ServicioService {
    baseUrl = 'http://localhost:4000/servicio-proveedor';

    getAll(){
        return axios.get(this.baseUrl + '/listar')
        .then(res => res.data); 
    }

    postAddServicioProveedor(servicio){
        return axios.post(this.baseUrl + '/agregar',servicio)
        .then(res => res.data);
    }
}

export default new ServicioService();