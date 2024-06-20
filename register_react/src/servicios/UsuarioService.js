import axios from "axios";

class UsuarioService {
    baseUrl = 'http://localhost:4000/usuario';

    getAll() {
        return axios.get(this.baseUrl + '/listar',{withCredentials: true})
            .then(res => res.data); 
    }

    addUser(nuevoUsuario) { 
        return axios.post(this.baseUrl + '/agregar', nuevoUsuario, {withCredentials: true})
            .then(res => res.data);
    }

    getInfo(){
        return axios.get(this.baseUrl + '/datos',{withCredentials: true})
            .then(res => res.data); 
    }

    getProveedorInfo(){
        return axios.get(this.baseUrl + '/proveedor/datos',{withCredentials: true})
            .then(res => res.data);
    }

    getClienteInfo(){
        return axios.get(this.baseUrl + '/cliente/datos',{withCredentials:true})
            .then(res => res.data);
    }
    
}

export default new UsuarioService();