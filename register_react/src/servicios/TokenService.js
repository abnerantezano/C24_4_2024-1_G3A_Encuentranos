import axios from 'axios';

class TokenService {
  baseUrl = 'http://localhost:4000/usuario';

  getToken() {
      return axios.get(this.baseUrl + '/token',{withCredentials: true})
          .then(res => res.data); 
  }

}

export default new TokenService();



