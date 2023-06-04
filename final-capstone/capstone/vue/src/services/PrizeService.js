import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000/prizes"
});

export default {

  listPrizes(prizes) {
    return http.get(`/prizes=${prizes}`);
  },

  addToPrizeList(prizes){
    return http.post(`/prizes=${prizes}`);
  },

  getPrizesEarned(finished){
    return http.get(`/prizes`, finished);
  },

  delete(prizes){
    return http.delete(prizes);
  }

}