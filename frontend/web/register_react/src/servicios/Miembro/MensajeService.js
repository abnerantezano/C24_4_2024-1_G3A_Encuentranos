import axios from 'axios';

class MensajeService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/mensaje';
    }

    getMensajes(chatId) {
        return axios.get(this.baseUrl + `/chat/${chatId}`, {withCredentials: true})
            .then(response => response.data)
            .catch(error => {
                console.error("Error al obtener todos los mensajes: " ,error);
                throw error;
            })
    }
    
    postMensaje(chatId, mensaje) {
        return axios.post(this.baseUrl + `/agregar/${chatId}`, mensaje, {withCredentials: true})
            .then(response => response.data)
            .catch(error => {
                console.error("Error al enviar mensaje: ",error);
                throw error;
            });
    }
}

const MensajeServiceInstance = new MensajeService();
export default MensajeServiceInstance;
