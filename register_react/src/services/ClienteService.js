import axios from 'axios';

export class ClienteService {
    baseUrl = 'http://localhost:8080/cliente';
    getAll(){
        return axios.get(this.baseUrl + 'add').then(res => res.data.data);
    }
}