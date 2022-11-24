import axios from 'axios';
import ApiService from '../services/api.service';

const API_URL = ApiService.API_URL;

class AuthService {
    
    login(loginForm) {
        return axios
            .post(API_URL + "auth/signin", loginForm)
            .then(response => {
                if (response.data.accessToken) {
                    sessionStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            });
    }
    logout() {
        sessionStorage.removeItem('user');
    }
    register(registerForm) {
        return axios.post(API_URL + "auth/signup", registerForm);
    }
}
export default new AuthService();