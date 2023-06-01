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
      <img src="/images/Untitled_Artwork.jpg">

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
  border: 3px;
  border-style:groove;
   background-image: url('https://img.freepik.com/premium-photo/natural-recycled-paper-texture-background_118047-8737.jpg');
    font-family: 'Libre Baskerville', serif;
    font: RGB(88,85,99);
}
.form-input-group {
  font-family: 'Libre Baskerville', serif;
  margin-bottom: 1rem;
  color: RGB(88,85,99);
}
label {
   font-family: 'Libre Baskerville', serif;
  margin-right: 0.5rem;
  font: RGB(88,85,99);
}
.image{
 display: flex;
 flex: row;
 width: 1902px;
 height: 645px;
 padding: 0px;
 margin: 0px;
}

</style>