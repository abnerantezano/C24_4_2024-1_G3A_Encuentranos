import axios from 'axios';

class NotificacionService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/notificacion';
    }

    postNotificacion(notificacion) {
        return axios.post(`${this.baseUrl}/agregar`, notificacion, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al crear notificacion: ', error);
                throw error;
            });
    }

    getNotificacionesProveedor(idProveedor) {
        return axios.get(`${this.baseUrl}/proveedor/${idProveedor}`, { withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener notificacion del proveedor: ', error);
                throw error;
            });
    }

    getNotificacionesCliente(idCliente) {
        return axios.get(`${this.baseUrl}/cliente/${idCliente}`, { withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener notificacion del cliente: ', error);
                throw error;
            });
    }

    deleteNotificacion(idNotificacion) {
        return axios.delete(`${this.baseUrl}/eliminar/${idNotificacion}`, {withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al eliminar notificacion: ', error);
                throw error;
            });
    }

}

const NotificacionServiceInstance = new NotificacionService();
export default NotificacionServiceInstance;
