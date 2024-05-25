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
        return axios.get(`${this.baseUrl}/buscar/${idservicio}/${idproveedor}`);
    }

    //TENER LA LISTA DE LOS SERVICIOS NO REGISTRADOS POR EL PROVEEDOR
    getServicioSinRegistrar(idproveedor){
        return axios.get(`${this.baseUrl}/servicios-no-registrados/${idproveedor}`);
    }

    //TENER LA LISTA DE LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
    getServicioRegistrados(idproveedor){
        return axios.get(`${this.baseUrl}/servicios-registrados/${idproveedor}`);
    }

    //TENER EL MAYOR PRECIO
    getPrecioMayor(){
        return axios.get(this.baseUrl + '/filtrar/alto',{withCredentials: true})
    }

    //TENER EL MENOR PRECIO
    getPrecioMenor(){
        return axios.get(this.baseUrl + '/filtrar/bajo',{withCredentials: true})
    }

    //FILTRO DEL PRECIO
    getFiltroPrecio(precio){
        return axios.get(`${this.baseUrl}/filtrar/${precio}`)
    }
}

export default new ServicioProveedorService();