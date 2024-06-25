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
    addUser(nuevoUsuario) { 
        return axios.post(this.baseUrl + '/agregar', nuevoUsuario, {withCredentials: true})
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

    putImagen(idUsuario, imagen) {
        const formData = new FormData();
        formData.append('imagen', imagen);
    
        return axios.put(`${this.baseUrl}/actualizar/${idUsuario}`, formData, {
          withCredentials: true,
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(res => res.data)
        .catch(error => {
          console.error('Error al actualizar la imagen: ', error);
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
