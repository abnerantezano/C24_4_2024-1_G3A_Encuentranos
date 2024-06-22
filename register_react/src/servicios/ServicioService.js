import axios from 'axios';

class ServicioService {

  constructor() {
    this.baseUrl = 'http://localhost:4000/servicio';
  }

  getAll() {
    return axios.get(this.baseUrl + '/listar', { withCredentials: true })
      .then(res => res.data)
      .catch(error => {
        console.error('Error al obtener servicios: ', error);
        throw error;
      });
  }
}

const servicioServiceInstance = new ServicioService();

export default servicioServiceInstance;
