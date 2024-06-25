import axios from "axios";

class Servicio {
  
  constructor(){
    this.baseUrl = "http://localhost:8000/api/v1/servicios";
  }

  getLista() {
    return axios
      .get(this.baseUrl)
      .then((res) => res.data);
  }

  postServicio(servicio) {
    return axios
      .post(this.baseUrl, servicio)
      .then((res) => res.data);
  }

  deleteServicio(id_servicio) {
    return axios
      .delete(this.baseUrl + `/${id_servicio}`)
      .then((res) => res.data);
  }

  putServicio(datos, id_servicio) {
    return axios
      .put(this.baseUrl + `/${id_servicio}`, datos)
      .then((res) => res.data);
  }
}

export default new Servicio();
