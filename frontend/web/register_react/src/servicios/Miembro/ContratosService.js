import axios from 'axios';

class ContratoService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/contrato';
    }

    getProveedores(idProveedor) {
        return axios.get(`${this.baseUrl}/listar/proveedor/${idProveedor}`, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener cliente: ', error);
                throw error;
            });
    }

    getClientes(idCliente) {
        return axios.get(`${this.baseUrl}/listar/proveedor/${idCliente}`, { withCredentials: true })
            .then(res => res.data)
            .catch(error => {
                console.error('Error al obtener cliente: ', error);
                throw error;
            });
    }

    postContrato(contrato) {
        return axios.post(`${this.baseUrl}/crear`, contrato, {withCredentials:true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al crear contrato: ', error);
                throw error;
            })
    }

    putAceptarContrato(idContrato) {
        return axios.put(`${this.baseUrl}/aceptar-proveedor/${idContrato}`, {withCredentials:true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al aceptar contrato contrato: ', error);
                throw error;
            })
    }

    putDenegarContrato(idContrato) {
        return axios.put(`${this.baseUrl}/denegar-proveedor/${idContrato}`, {withCredentials:true})
            .then(res => res.data)
            .catch(error => {
                console.error('Error al denegar contrato: ', error);
                throw error;
            })
    }
}

const ContratoServiceInstance = new ContratoService();
export default ContratoServiceInstance;
