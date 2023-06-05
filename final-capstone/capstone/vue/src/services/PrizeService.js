import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000/prizes"
});

export default {

  listPrizes(prize) {
    return http.get(`/prizes=${prize}`);
  },

  addToPrizeList(prize){
    return http.post(`/prizes=${prize}`);
  },

  getPrizesEarned(username, finished){
    return http.get(`/prizes/${username}`, finished);
  },

  delete(prizes){
    return http.delete(prizes);
  }

}