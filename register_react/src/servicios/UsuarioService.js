import axios from "axios";

class UsuarioService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/usuario';
    }

    getAll() {
        return axios.get(this.baseUrl + '/listar', {withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener usuarios: ', error);
                throw error;
            });
            
    }

    addUser(nuevoUsuario) { 
        return axios.post(this.baseUrl + '/agregar', nuevoUsuario, {withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al agregar usuario: ', error);
                throw error;
            });
    }

    getInfo() {
        return axios.get(this.baseUrl + '/datos', {withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener datos del usuario: ', error);
                throw error;
            });
    }

    getProveedorInfo() {
        return axios.get(this.baseUrl + '/proveedor/datos', {withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener datos del proveedor: ', error);
                throw error;
            });
    }

    getClienteInfo() {
        return axios.get(this.baseUrl + '/cliente/datos', {withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener datos del cliente: ', error);
                throw error;
            });
    }
}

const usuarioServiceInstance = new UsuarioService();

export default usuarioServiceInstance;
