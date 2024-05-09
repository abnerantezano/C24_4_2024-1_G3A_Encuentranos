import axios from 'axios';

class DistritoService {

    baseUrl = 'http://localhost:4000/distrito';

    getAll() {
        return axios.get(this.baseUrl + '/listar',{withCredentials: true})
            .then(res => res.data);     
    }
}

export default new DistritoService();
