import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000"
});

export default {

  list(id) {
    return http.get(`/family/${id}`);
  },

  get(id) {
    return http.get(`/docs/${id}`)
  }

}