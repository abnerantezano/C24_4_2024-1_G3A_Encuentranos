// src/services/authService.js
import axios from 'axios';
import jwtDecode from 'jwt-decode';

class AuthService {
    constructor() {
        this.springBootBaseUrl = 'http://localhost:4000';
        this.djangoBaseUrl = 'http://localhost:8000/api/v1';
    }

    async login(username, password, role) {
        let response;
        if (role === 'admin') {
            response = await axios.post(`${this.djangoBaseUrl}/api/token/`, { username, password });
            localStorage.setItem('adminToken', response.data.access);
        } else {
            response = await axios.post(`${this.springBootBaseUrl}/oauth/token`, {
                grant_type: 'password',
                username,
                password,
                client_id: 'your-client-id',
                client_secret: 'your-client-secret'
            });
            localStorage.setItem('userToken', response.data.access_token);
        }
    }

    getToken() {
        return localStorage.getItem('adminToken') || localStorage.getItem('userToken');
    }

    getUserRole() {
        const token = this.getToken();
        if (token) {
            const decodedToken = jwtDecode(token);
            return decodedToken.role || decodedToken.authorities[0];
        }
        return null;
    }

    isAuthenticated() {
        return !!this.getToken();
    }

    logout() {
        localStorage.removeItem('adminToken');
        localStorage.removeItem('userToken');
    }
}

const authService = new AuthService();
export default authService;
