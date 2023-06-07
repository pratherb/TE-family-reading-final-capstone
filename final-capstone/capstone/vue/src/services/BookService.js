import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000/book"
});

export default {

  listBooksByTitle(title) {
    return http.get(`/title=${title}`);
  },

  listBooksByPublisher(publisher) {
    return http.get(`/publisher=${publisher}`);
  },

  get(isbn) {
    console.log("reached the get method");
    return http.get(`/isbn=${isbn}`);
  },

  addToReadingList(isbn, username){
    console.log(isbn, username);
    return http.post(`/isbn=${isbn}?username=${username}`);
  },

  delete(isbn){
    return http.delete(`/books/${isbn}`);
  }

}