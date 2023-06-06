<template>
  <div id="login">
    <h1 class="welcome">
      Welcome! Please Log In!
      <div class="logo">
        <img src="../images/sharpened-transparent-logo.png" />
      </div>
    </h1>
    <form @submit.prevent="login">
      <div class= "alert" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div class="alert" role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="background_for_image">
        <div class="all-input">
          <div class="form-input-group" id="username">
            <label for="username">👤 Username</label>
            <input
              type="text"
              id="username"
              v-model="user.username"
              required
              autofocus
            />
          </div>
          <div class="form-input-group" id="password">
            <label for="password">🔒 Password</label>
            <input
              type="password"
              id="password"
              v-model="user.password"
              required
            />
          </div>
          <button type="submit">Sign in</button>
          <p>
            <router-link class="signup" :to="{ name: 'register' }"
              >Need an account? Sign up.</router-link
            >
          </p>
        </div>
        <div class="running-board">
          <p>
            📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘
            📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗
            📘 📓📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒
            📗 📘 📓 📕
          </p>
        </div>
      </div>
    </form>
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
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push({path: "/members"});
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.cdnfonts.com/css/socake");
@import url("https://fonts.googleapis.com/css2?family=Montserrat&display=swap");

.background_for_image {
  background: rgb(115, 147, 126);
  margin: 0;
  padding: 0;
}

form {
  background: rgb(115, 147, 126);
  border: 3px;
  border-style: groove;
  color: rgb(88, 85, 99);
  font-family: "Montserrat", sans-serif;
  font: rgb(88, 85, 99);
  width: 100%;
  height: 100%;
}
.all-input {
  display: flex;
  background-image: url("../images/open_book_transparent-removebg-preview.png");
  background-repeat: no-repeat;
  flex-direction: column;
  flex-wrap: wrap;
  align-items: center;
  align-content: center;
  background-position: center;
  height: 400px;
}
.form-input-group {
  font-family: "Montserrat", sans-serif;
  margin-bottom: 1rem;
  color: rgb(88, 85, 99);
  margin-top: 70px;
  margin-right: 20px;
}
.running-board {
  margin-top: 150px;
  background: rgb(115, 147, 126);
}

label {
  font-family: "Montserrat", sans-serif;
  margin-right: 0.5rem;
  color: rgb(88, 85, 99);
}
.logo {
  display: flex;
  align-items: center;
}

img {
  width: 400px;
  height: 450px;
}

.welcome {
  display: flex;
  justify-content: center;
  background: rgba(254, 209, 113, 0.5);
  padding-top: 80px;
  height: 200px;
  width: 100%;
  font-size: 60px;
  font-family: "Socake", sans-serif;
  color: rgb(88, 85, 99);
}
.alert{
  text-decoration: none;
  color: black;
  text-align: center;
}
.signup{
  text-decoration: underline;
  color:black;
}
</style>
