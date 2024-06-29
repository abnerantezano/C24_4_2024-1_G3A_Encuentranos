import axios from "axios";

class Usuario {
  
  constructor(){
    this.baseUrl = "http://localhost:8000/api/v1/usuarios";
  }

  getLista() {
    return axios
      .get(this.baseUrl)
      .then((res) => res.data);
  }

  deleteUsuario(id_usuario) {
    return axios
      .delete(this.baseUrl + `/${id_usuario}`)
      .then((res) => res.data);
  }

  putUsuario(datos, id_usuario) {
    return axios
      .put(this.baseUrl + `/${id_usuario}`, datos)
      .then((res) => res.data);
  }
}

export default new Usuario();
