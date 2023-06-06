import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:9000/user"
});

export default {
    getUserReadingActivity(username) {
        return http.get(`/${username}/activity`);
    },

    create(activity, username) {
        return http.post(`/${username}/activity`, activity);
    },

    getTotalMinutesReadPerUser(username) {
        return http.get(`/${username}/activity/total`);
    }
  
  }