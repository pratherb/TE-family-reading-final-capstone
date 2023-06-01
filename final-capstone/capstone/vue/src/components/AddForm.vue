<template>
  <div>
      <div id="add" class="text-center">
    <form v-on:submit.prevent="addForm">
      <h1>Add Family Member</h1>
      <div role="alert" v-if="addErrors">
        {{ addErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="firstname">First Name</label>
        <input type="text" id="firstname" v-model="user.firstName" required />
      </div>
      <div class="form-input-group">
        <label for="lastname">Last Name</label>
        <input type="text" id="lastname" v-model="user.lastName" required />
      </div>
      <button type="submit">Create Family Member</button>
    </form>
  </div>
  </div>
</template>

<script>
import userService from '../services/UserService';

export default {
  name: 'addform',
  data() {
    return {
      user: {
        username: '',
        password: '',
        firstName: '',
        lastName: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    }
  },
  methods: {
      addForm() {
          const newMember = {
              username: this.user.username,
              password: this.user.password,
              firstName: this.user.firstName,
              lastName: this.user.lastName
          };
          if (this.username != '') {
              userService 
                .add(newMember)
                .then((response) => {
                    if (response.status === 201) {
                        this.$router.push(`/user/${newMember.username}`); 
                    }
                })
          } 
      }
  }
}
</script>

<style>

</style>