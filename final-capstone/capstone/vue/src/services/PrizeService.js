import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000/prize"
});

export default {

  //List prizes for group type - both/child/parent
  listPrizes(groupType) {
    return http.get(`/${groupType}`);
  },

  addToPrizeList(prize){
    return http.post(`/`, prize);
  },

  getPrizesEarned(username, finished){
    return http.get(`/prizes/${username}`, finished);
  },

  delete(prizes){
    return http.delete(prizes);
  }

}