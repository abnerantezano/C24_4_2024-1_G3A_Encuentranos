import axios from 'axios';

export class ClienteService {
    baseUrl = 'http://localhost:8080/servicio';
    getAll(){
        return axios.get(this.baseUrl + 'add').then(res => res.data.data);
    }
}