import axios from 'axios';

export class ProveedorService {
    baseUrl = 'http://localhost:4000/proveedor';
    
    addProveedor(datos) {
        return axios.post(this.baseUrl + '/agregar', datos, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                if (error.response) {
                    // La solicitud fue hecha y el servidor respondió con un código de estado fuera del rango 2xx
                    console.log('Error de respuesta del servidor:', error.response.data);
                    console.log('Código de estado:', error.response.status);
                } else if (error.request) {
                    // La solicitud fue hecha pero no se recibió ninguna respuesta
                    console.log('No se recibió respuesta del servidor:', error.request);
                } else {
                    // Ocurrió un error al configurar la solicitud
                    console.log('Error al configurar la solicitud:', error.message);
                }
                console.log('Error detallado:', error.config);
                throw error; // Lanza el error para que el código que llama pueda manejarlo según sea necesario
            });
    }
}

export default new ProveedorService();