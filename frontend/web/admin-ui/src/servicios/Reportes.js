import axios from "axios";

class Reportes {
    constructor() {
        this.baseUrl = "http://localhost:8000/api/v1/reportes";
    };

    getTopProveedores() {
        return axios
            .get(this.baseUrl + '/top-proveedores/')
            .then((res) => res.data);
    }

    getServiciosProveedores() {
        return axios
            .get(this.baseUrl + '/servicios-proveedores/')
            .then((res) => res.data);
    }

    getUsuariosRecientes() {
        return axios
            .get(this.baseUrl + '/usuarios-recientes/')
            .then((res) => res.data);
    }

    getContratosRecientes() {
        return axios
            .get(this.baseUrl + '/contratos-recientes/')
            .then((res) => res.data);
    }

    getClientesContratos() {
        return axios
            .get(this.baseUrl + '/clientes-contratos/')
            .then((res) => res.data);
    }


}

export default new Reportes();