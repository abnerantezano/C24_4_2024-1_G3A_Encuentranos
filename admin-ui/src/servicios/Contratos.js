import axios from "axios";

class ContratoService {
  
    constructor(){
        this.baseUrl = "http://localhost:8000/api/v1/contratos"; 
    }

    getLista() {
        return axios
        .get(this.baseUrl)
        .then((res) => res.data);
    }

    deleteContrato(id_contrato) {
        return axios
        .delete(this.baseUrl + `/${id_contrato}`)
        .then((res) => res.data);
    }

    putContrato(datos, id_contrato) {
        return axios
        .put(this.baseUrl + `/${id_contrato}`, datos)
        .then((res) => res.data);
    }
    }

export default new ContratoService();
