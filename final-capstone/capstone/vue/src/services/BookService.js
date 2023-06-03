import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000/book/isbn="
});

export default {

  list() {
    return http.get('/book');
  },

  get(id) {
    return http.get(`/book/${id}`)
  }

}