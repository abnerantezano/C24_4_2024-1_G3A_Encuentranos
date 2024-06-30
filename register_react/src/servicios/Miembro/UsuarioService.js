import axios from "axios";

class UsuarioService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/usuario';
    }

    //OBTENER TODOS LOS USUARIOS
    getAll() {
        return axios.get(this.baseUrl + '/listar', {withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener usuarios: ', error);
                throw error;
            });
            
    }

    //CREAR UN USUARIO
    addUser(formData) { 
        return axios.post(this.baseUrl + '/agregar', formData, {headers: {'Content-Type': 'multipart/form-data'}}, {withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al agregar usuario: ', error);
                throw error;
            });
    }

    //OPTENER EL TIPO DE USUARIO
    getTipoUsuario(idUsuario){
        return axios.get(this.baseUrl + `/tipo/${idUsuario}`, {withCredentials: true})
        .then(res => res.data)
        .catch(error => {
            console.error('Error al agregar usuario: ', error);
            throw error;
        });
    }

    //OBTENER LA INFORMACIÓN DEL USUARIO
    getInfo() {
        return axios.get(this.baseUrl + '/datos', {withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener datos del usuario: ', error);
                throw error;
            });
    }

    // ACTUALIZAR CONTRASEÑA DEL USUARIO
    putUsuario(idUsuario, datos) {
        return axios.put(this.baseUrl + `/actualizar-contrasena/${idUsuario}`, null, { params: datos, withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al actualizar contraseña: ', error);
                throw error;
            });
    }

    //ACTUALIZAR IMAGEN DEL USUARIO
    putImagen(idUsuario, formData) {
        return axios.put(this.baseUrl + `/actualizar/${idUsuario}`, formData, {headers: {'Content-Type': 'multipart/form-data'}, withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al actualizar la imagen del usuario: ', error);
                throw error;
            });
    }

    //OBTENER LA INFORMACIÓN DE UN USUARIO TIPO PROVEEDOR
    getProveedorInfo() {
        return axios.get(this.baseUrl + '/proveedor/datos', {withCredentials: true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener datos del proveedor: ', error);
                throw error;
            });
    }

    //OBTENER LA INFORMACIÓN DE UN USUARIO TIPO CLIENTE
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
