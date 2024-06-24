import axios from 'axios';

class TokenService {

  constructor() {
    this.baseUrl = 'http://localhost:4000/token';
  }

  getToken() {
    return axios.get(this.baseUrl, { withCredentials: true })
      .then(res => res.data)
      .catch(error => {
        console.error('Error al obtener el token del usuario: ', error);
        throw error;
    });
  }
  
}

const tokenServiceInstance = new TokenService();

export default tokenServiceInstance;
