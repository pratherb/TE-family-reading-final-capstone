<template>
  <div id="register" class="text-center">
    <div class="headlogo">
       <h1 class="head">Create Account</h1>
      <img class="regheadLogo" src="../images/sharpened-transparent-logo.png" />
    </div>
    <form @submit.prevent="register">
      <div class="behind">
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="firstName">First Name: </label>
        <input
          type="text"
          id="firstName"
          v-model="user.firstName"
          required
          autofocus
        />
      </div>
      <div class="form-input-group">
        <label for="lastName">Last Name: </label>
        <input
          type="text"
          id="lastName"
          v-model="user.lastName"
          required
          autofocus
        />
      </div>
      <div class="form-input-group">
        <label for="email">Email: </label>
        <input
          type="email"
          id="email"
          v-model="user.email"
          required
          autofocus
        />
      </div>
      <div class="form-input-group">
        <label for="username">Username: </label>
        <input
          type="text"
          id="username"
          v-model="user.username"
          required
          autofocus
        />
      </div>
      <div class="form-input-group">
        <label for="password">Password: </label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password: </label>
        <input
          type="password"
          id="confirmPassword"
          v-model="user.confirmPassword"
          required
        />
      </div>
      <button class="submit" type="submit">Create Account</button>
      <p>
        <router-link class="toLogin" :to="{ name: 'login' }"
          >Already have an account? Log in.</router-link
        >
      </p>
      </div>
    </form>
     <p class="running">
      📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕
       📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓📕 📙
      📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕
    </p>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        firstName: "",
        lastName: "",
        email: "",
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");
@import url("https://fonts.cdnfonts.com/css/socake");

.behind{
   background: rgba(232, 123, 31, 0.8);
  display: flex;
  flex-direction: column;
  padding-top: 25px;
  align-items: flex-start;
  width: 1100px;
  height: 550px;
  justify-content: space-between;
  border-radius: 8px;
  border: solid 3px rgb(182, 90, 10);
  margin-top:30px;
}
.headlogo{
  justify-content: center;
  display: flex;
  flex-direction: row;
    align-items: center;
    background:rgba(254, 209, 113, 0.5);
    border-left: 60px solid rgb(115, 147, 126);
    border-right:60px solid rgb(115, 147, 126);
    border-top: 5px solid rgb(115, 147, 126);
    height: 250px;
    border-radius:3px;
}
.head{
   font-family: "Socake", sans-serif;
   font-size: 60px;
  color: rgb(88, 85, 99);
}
form {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  align-content: center;
  align-items: baseline;
  background: rgb(115, 147, 126);
}

.form-input-group {
  
  display: flex;
  border: solid, 1px, black;
  margin-bottom: 1rem;
  color: rgb(59, 57, 66);
  font-weight: bold;
  font-size: 20px;
}
label {
  margin-right: 0.5rem;
}
img {
  width: 300px;
  height:350px;
}
.toLogin{
  text-decoration: underline;
  color:black;
  font-family: "Montserrat", sans-serif;
}
.running {
  display: flex;
  background: rgb(115, 147, 126);
  height: 95px;
  margin-top: 0;
  padding-top:20px;
}
  .submit{
transition-duration: 0.4s;
  padding: 12px 28px;
  margin-left:30px;
  border: 2.5px solid rgb(182, 90, 10);
  background-color: rgba(255, 200, 154, 0.8);
  font-family: "Montserrat", sans-serif;
  color: rgb(48, 46, 49);
  font-weight: bold;
  font-size:15px ;
}
.submit:hover{
  background-color: rgb(182, 90, 10);
}

</style>
