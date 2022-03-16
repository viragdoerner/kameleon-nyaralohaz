import axios from 'axios';
import authHeader from './auth-header';

class ApiService {
  API_URL = 'http://localhost:8080/';

  getPublicData(url) {
    return axios.get(API_URL + url);
  }
  getSecuredData(url){
    return axios.get(API_URL + url, { headers: authHeader() });
  }
  postToSecuredAPI(url, param) {
    return axios.post(API_URL + url, param, { headers: authHeader() });
  }
  deleteSecuredData(url) {
    return axios.post(API_URL + url, { headers: authHeader() });
  }
}
export default new ApiService();