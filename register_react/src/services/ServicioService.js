import axios from 'axios';

class ServicioService {
    baseUrl = 'http://localhost:4000/servicio';
    
    getAll(){
        return axios.get(this.baseUrl + '/listar')
        .then(res => res.data);
    }
}

export default new ServicioService();