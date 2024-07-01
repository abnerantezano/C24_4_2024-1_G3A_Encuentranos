import axios from 'axios';

class ChatService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/chat';
    }

    postChat(chat) {
        return axios.post(this.baseUrl + '/agregar', chat , {withCredentials: true})
            .then(response => response.data)
            .catch(error => {
                console.error("Error al crear chat: " ,error);
                throw error;
            })
    }
    
    getPorCliente(idcliente) {
        return axios.get(`${this.baseUrl}/cliente/${idcliente}`, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener chats por cliente: ', error);
                throw error;
            });
    }

    getPorProveedor(idProveedor) {
        return axios.get(`${this.baseUrl}/proveedor/${idProveedor}`, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener chats oor proveedor: ', error);
                throw error;
            });
    }
}

const ChatServiceInstance = new ChatService();
export default ChatServiceInstance;
