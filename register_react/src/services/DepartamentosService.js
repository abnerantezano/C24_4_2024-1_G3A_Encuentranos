import axios from 'axios';

const CLIENTE_BASE_REST_API_URL = 'https://young-inlet-40292-f42588231bef.herokuapp.com';

class DepartamentoService {

    getDepartamentos() {
        return axios.get(CLIENTE_BASE_REST_API_URL);
    }
    
    getProvincias(departamento) {
        return axios.get(`${this.baseUrl}/provincias/${departamento}`)
            .then(res => res.data);
    }

    getDistritos(departamento,provincia) {
        return axios.get(`${this.baseUrl}/distritos/${provincia}/${departamento}`)
            .then(res => res.data);
    }
}

export default new DepartamentoService();
