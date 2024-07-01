import axios from 'axios';

class DetalleCalificacionService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/detalle-calificacion';
    }   

    postDetalleCalificacion(detalleCalificacion) {
        return axios.post(`${this.baseUrl}/agregar`, detalleCalificacion, { withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al agregar el detalle-calificacion: ', error);
                throw error;
            })
    }

    getCalificaciones(idProveedor) {
        return axios.get(`${this.baseUrl}/buscar/${idProveedor}`, { withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener reseñas: ', error);
                throw error;
            })
    }
}

const DetalleCalificacionServiceInstance = new DetalleCalificacionService();
export default DetalleCalificacionServiceInstance;
