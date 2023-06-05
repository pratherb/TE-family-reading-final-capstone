<template>
  <div id="register" class="text-center">
    <div class="regLogo">
       <h1 class="create_account" > Create Account: </h1>
      <img src="../images/sharpened-transparent-logo.png">
    </div>
    <form @submit.prevent="register">
     
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="firstName">First Name: </label>
        <input type="text" id="firstName" v-model="user.firstName" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="lastName">Last Name: </label>
        <input type="text" id="lastName" v-model="user.lastName" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="email">Email: </label>
        <input type="email" id="email" v-model="user.email" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="username">Username: </label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password: </label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password: </label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit">Create Account</button>
      <p><router-link :to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
@import url('https://fonts.cdnfonts.com/css/socake');
@import url('https://fonts.googleapis.com/css2?family=Montserrat&display=swap');

.create_account{
  font-size: 55px;
  display: flex;
  color: rgb(88,85,99);
  width: 100%;
  height:200%;
   font-family: 'Socake', sans-serif;
}
form{
  background: rgb(115, 147, 126);
      display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    align-content: flex-start;
    align-items: baseline;
}
.form-input-group {
display: flex;
font-family: 'Montserrat', sans-serif;
border: solid, 1px, black;
margin-bottom: 1rem;
 color: rgb(88,85,99);
}
label {
  margin-right: 0.5rem;
}
.regLogo{
  display: flex;
  background: rgba(254, 209, 113, 0.5);
  justify-content: flex-end;
  padding: none;
  margin: none;
  border:2px;
  border-style: solid;
  border-color: black;
}
img{
  width: 150px;
  padding: none;
}

</style>
