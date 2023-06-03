import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000/book"
});

export default {

  listBooksByTitle(title) {
    return http.get(`/title=${title}`);
  },

  get(isbn) {
    console.log("reached the get method")
    return http.get(`/isbn=${isbn}`);
  },

  addToReadingList(isbn, username){
    return http.post(`/isbn=${isbn}`, username);
  },

  getFamilyReadingList(finished){
    return http.get(`/all`, finished);
  },

  getUserReadingList(username, finished){
    return http.get(`/user/${username}`, finished);
  },

  delete(isbn){
    return http.delete(isbn);
  }

}