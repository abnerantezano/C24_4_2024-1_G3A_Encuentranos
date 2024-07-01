import axios from "axios";

class Servicio {
    constructor() {
        this.baseUrl = "http://localhost:8000/api/v1/proveedores/";
    };

    getLista() {
        return axios
        .get(this.baseUrl)
        .then((res) => res.data);
    }

}

export default new Servicio();