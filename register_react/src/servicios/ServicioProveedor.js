import axios from 'axios';

class ServicioProveedorService {
    
    constructor() {
        this.baseUrl = 'http://localhost:4000/servicio-proveedor';
    }

    //LISTAR SERVICIOS PROVEEDORES
    getAll() {
        return axios.get(this.baseUrl + '/listar', { withCredentials: true })
        .then(res => res.data)
        .catch(error => {
            console.error('Error al obtener servicios-proveedores: ', error);
            throw error;
        });
    }

    //AGREGAR SERVICIOS PROVEEDORES (ARRAY)
    postAddServicioProveedor(servicio) {
        return axios.post(this.baseUrl + '/agregar', servicio, { withCredentials: true })
        .then(res => res.data)
        .catch(error => {
            console.error('Error al agregar servicio-proveedor: ', error);
            throw error;
        });
    }

    //BUSCAR UN SERVICIO POR EL ID DEL MISMO Y EL ID DEL PROVEEDOR
    getBuscarServicioProveedor(idservicio, idproveedor) {
        return axios.get(`${this.baseUrl}/buscar/${idservicio}/${idproveedor}`, { withCredentials: true })
        .then(res => res.data)
        .catch(error => {
            console.error('Error al obtener servicio-proveedor: ', error);
            throw error;
        });
    }

    //TENER LA LISTA DE LOS SERVICIOS SEGUN EL PROVEEDOR
    getServiciosDisponibles(idproveedor) {
        return axios.get(`${this.baseUrl}/buscar/${idproveedor}`, { withCredentials: true })
        .then(res => res.data)
        .catch(error => {
            console.error('Error al obtener servicio según proveedor: ', error);
            throw error;
        });
    }

    //TENER LA LISTA DE LOS SERVICIOS NO REGISTRADOS POR EL PROVEEDOR
    getServicioSinRegistrar(idproveedor) {
        return axios.get(`${this.baseUrl}/servicios-no-registrados/${idproveedor}`, { withCredentials: true })
        .then(res => res.data)
        .catch(error => {
            console.error('Error al obtener servicios sin registrar del proveedor: ', error);
            throw error;
        });
    }

    //TENER LA LISTA DE LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
    getServicioRegistrados(idproveedor) {
        return axios.get(`${this.baseUrl}/servicios-registrados/${idproveedor}`, { withCredentials: true })
        .then(res => res.data)
        .catch(error => {
            console.error('Error al obtener servicios registrados del proveedor: ', error);
            throw error;
        });
    }

    //TENER EL MAYOR PRECIO
    getPrecioMayor() {
        return axios.get(this.baseUrl + '/filtrar/alto', { withCredentials: true })
        .then(res => res.data)
        .catch(error => {
            console.error('Error al obtener el mayor precio: ', error);
            throw error;
        });
    }

    //TENER EL MENOR PRECIO
    getPrecioMenor() {
        return axios.get(this.baseUrl + '/filtrar/bajo', { withCredentials: true })
        .then(res => res.data)
        .catch(error => {
            console.error('Error al obtener el menor precio: ', error);
            throw error;
        });
    }
    
}

const servicioProveedorServiceInstance = new ServicioProveedorService();

export default servicioProveedorServiceInstance;
