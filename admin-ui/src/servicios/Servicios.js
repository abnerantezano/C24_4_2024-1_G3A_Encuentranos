import axios from 'axios';

class Servicio {
    
    baseUrl = 'http://localhost:8000/servicios';
    
    getLista(){
        return axios.get(this.baseUrl,{withCredentials: true})
        .then(res => res.data);
    }

    getServicio(){
        return axios.get(this.baseUrl,{withCredentials: true})
        .then(res => res.data);
    }
}

export default new Servicio();