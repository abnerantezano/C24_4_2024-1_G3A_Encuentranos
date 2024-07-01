import axios from 'axios';

class CalificacionService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/calificacion';
    }   

    postCalificacion(calificacion) {
        return axios.post(`${this.baseUrl}/agregar`, calificacion, { withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al agregar la calificacion: ', error);
                throw error;
            })
    }
}

const CalificacionServiceInstance = new CalificacionService();
export default CalificacionServiceInstance;
