import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000/"
});

export default {

    add(user) {
      return axios.post('/user', user)
    },
  
    update(user) {
      return http.put(`/user/${user.username}`, user);
    }
  
  }