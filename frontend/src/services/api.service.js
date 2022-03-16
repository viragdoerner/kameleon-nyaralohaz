import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/';

class ApiService {

    getPublicData(url) {
        return axios.get(API_URL + url);
    }
    getSecuredData(url) {
        return axios.get(API_URL + url, { headers: authHeader() });
    }
    postToSecuredAPI(url, param) {
        return axios.post(API_URL + url, param, { headers: authHeader() });
    }
    putToSecuredAPI(url, param) {
        return axios.put(API_URL + url, param, { headers: authHeader() });
    }
    deleteSecuredData(url) {
        return axios.post(API_URL + url, { headers: authHeader() });
    }
}
export default new ApiService();