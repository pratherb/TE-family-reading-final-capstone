<template>
  <div id="login">
      
    <form @submit.prevent="login">
      <h1 >Log In</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">👤 Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">🔒 Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Sign in</button>
      <p>
      <router-link :to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
      <div class="running-board">
        <p> 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 </p>
      </div>
    </form>
    <div class="image">
      <img src="../images/sharpened-transparent-logo.png">
    </div>
    </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Libre+Baskerville&display=swap');


form{
  background: linear-gradient(to right, rgba(122, 85, 85, 0.3), rgba(	255, 165, 0,0.3), rgba(255,255,0,0.3), rgba(50,205,50,0.3), rgba(0,191,255,0.3), rgba(148,0,211,0.3));
  border: 3px;
  border-style:groove;
  color: rgb(88,85,99);
    font-family: 'Libre Baskerville', serif;
    font: rgb(88,85,99);
}
.form-input-group {
  font-family: 'Libre Baskerville', serif;
  margin-bottom: 1rem;
  color: rgb(88,85,99);
}
label {
   font-family: 'Libre Baskerville', serif;
  margin-right: 0.5rem;
  font: rgb(88,85,99);
}
.image{
   background-image:url('https://img.freepik.com/premium-photo/vintage-paper-texture-background-with-copy-space-space-text_213524-427.jpg?w=1380');
  display:flex;
justify-content: center;
}
img{
  width:30%
}
</style>