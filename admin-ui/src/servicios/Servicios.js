import axios from "axios";

class Servicio {
  baseUrl = "http://localhost:8000/encuentranos/";

  getLista() {
    return axios
      .get(this.baseUrl + "api/v1/servicios/")
      .then((res) => res.data);
  }

  postServicio(servicio) {
    return axios
      .post(this.baseUrl + "api/v1/servicios/", servicio)
      .then((res) => res.data);
  }

  deleteServicio(id_servicio) {
    return axios
      .delete(this.baseUrl + `api/v1/servicios/detalle/${id_servicio}`)
      .then((res) => res.data);
  }

  putServicio(datos, id_servicio) {
    return axios
      .put(this.baseUrl + `api/v1/servicios/detalle/${id_servicio}`, datos)
      .then((res) => res.data);
  }
}

export default new Servicio();
