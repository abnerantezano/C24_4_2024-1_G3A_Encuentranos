import axios from 'axios';

class ServicioProveedorService {
    baseUrl = 'http://localhost:4000/servicio-proveedor';

    //LISTAR SERVICIOS PROVEEDORES
    getAll(){
        return axios.get(this.baseUrl + '/listar',{withCredentials: true})
        .then(res => res.data); 
    }

    //AGREGAR SERVICIOS PROVEEDORES (ARRAY)
    postAddServicioProveedor(servicio){
        return axios.post(this.baseUrl + '/agregar',servicio,{withCredentials: true})
            .then(res => res.data);
    }

    //BUSCAR UN SERVICIO POR EL ID DEL MISMO Y EL ID DEL PROVEEDOR
    getBuscarServicioProveedor(idservicio,idproveedor){
        return axios.get(`${this.baseUrl}/buscar/${idservicio}/${idproveedor}`, {withCredentials: true})
            .then(res => res.data);
    }

    //TENER LA LISTA DE LOS SERVICIOS SEGUN EL PROVEEDOR
    getServiciosDisponibles(idproveedor){
        return axios.get(`${this.baseUrl}/listar/${idproveedor}` , {withCredentials:true})
            .then(res => res.data);
    }

    //TENER LA LISTA DE LOS SERVICIOS NO REGISTRADOS POR EL PROVEEDOR
    getServicioSinRegistrar(idproveedor){
        return axios.get(`${this.baseUrl}/servicios-no-registrados/${idproveedor}`, {withCredentials: true})
            .then(res => res.data);
    }

    //TENER LA LISTA DE LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
    getServicioRegistrados(idproveedor){
        return axios.get(`${this.baseUrl}/servicios-registrados/${idproveedor}`, {withCredentials: true})
            .then(res => res.data);
    }

    //TENER EL MAYOR PRECIO
    getPrecioMayor(){
        return axios.get(this.baseUrl + '/filtrar/alto',{withCredentials: true})
            .then(res => res.data);
    }

    //TENER EL MENOR PRECIO
    getPrecioMenor(){
        return axios.get(this.baseUrl + '/filtrar/bajo',{withCredentials: true})
            .then(res => res.data);
    }

    //FILTRO DEL PRECIO
    getFiltroPrecio(precio){
        return axios.get(`${this.baseUrl}/filtrar/${precio}`,{withCredentials: true})
            .then(res => res.data);
    }
}

export default new ServicioProveedorService();