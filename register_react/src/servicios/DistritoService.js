import axios from 'axios';

class DistritoService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/distrito';
    }

    getAll() {
        return axios.get(this.baseUrl + '/listar', { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener distritos: ', error);
                throw error;
            });
    }
}

const DistritoServiceInstance = new DistritoService();

export default DistritoServiceInstance;
