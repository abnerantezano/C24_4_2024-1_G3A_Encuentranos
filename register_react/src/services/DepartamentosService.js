import axios from 'axios';

class DepartamentoService {

    baseUrl = 'https://young-inlet-40292-f42588231bef.herokuapp.com';
    
    getDepartamentos(){
        return axios.get(this.baseUrl + '/departamentos')
        .then(res => res.data.data);
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
