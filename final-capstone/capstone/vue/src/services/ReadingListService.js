import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000"
});

export default {
    
  getFamilyReadingList(finished) {
    return http.get(`/all`, finished);
  },

  getUserReadingList(username, finished) {
    return http.get(`/user/${username}`, finished);
  },

  markBookAsFinished(username, isbn) {
    return http.put(`/user/${username}`, isbn);
  },

  getNumBooksFinishedPerUser(username) {
    return http.get(`/user/${username}/completed`);
  }
}
