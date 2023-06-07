import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000"
});

export default {
    
  getFamilyReadingList(finished) {
    return http.get(`/all`, finished);
  },

  getUserReadingList(username) {
    return http.get(`/user/${username}`);
  },

  markBookAsFinished(username, isbn) {
    return http.put(`/user/${username}?isbn=${isbn}`);
  },

  getNumBooksFinishedPerUser(username) {
    return http.get(`/user/${username}/completed`);
  },

  getNumBooksFinishedPerFamily(id) {
    return http.get(`/members/${id}/books`)
  }
}
