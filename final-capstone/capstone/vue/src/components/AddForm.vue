<template>
  <div>
    <div id="add" class="text-center">
      <form class="members_form" v-on:submit.prevent="addForm">
        <div class="behind_form_text">
          <div class="form-input-group">
            <label class="add_label" for="username">Username: </label>
            <input type="text" id="username" v-model="user.username" required />
          </div>
          <div class="form-input-group">
            <label class="add_label" for="password">Password: </label>
            <input
              type="password"
              id="password"
              v-model="user.password"
              required
            />
          </div>
          <div class="form-input-group">
            <label class="add_label" for="firstname">First Name: </label>
            <input
              class="add_label"
              type="text"
              id="firstname"
              v-model="user.firstName"
              required
            />
          </div>
          <div class="form-input-group">
            <label class="add_label" for="lastname">Last Name: </label>
            <input type="text" id="lastname" v-model="user.lastName" required />
          </div>
          <div class="form-input-group">
            <label class="add_label" for="role">Role: </label>
            <select id="role" v-model="user.role" required>
              <option value="parent">Parent 🐓</option>
              <option value="child">Child 🐥</option>
            </select>
          </div>
          <br />
        </div>
        <br />
        <button class="add-member-button" type="submit">Add Family Member</button>
      </form>
    </div>
  </div>
</template>

<script>
import userService from "../services/UserService";

export default {
  name: "addform",
  data() {
    return {
      user: {
        username: "",
        password: "",
        firstName: "",
        lastName: "",
        role: "",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    addForm() {
      const newMember = {
        username: this.user.username,
        password: this.user.password,
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        role: this.user.role,
      };
      if (this.username != "") {
        userService.add(newMember).then((response) => {
          if (response.status === 200) {
            this.user = {
              username: "",
              password: "",
              firstName: "",
              lastName: "",
              role: "",
            };
            this.$router.push({ path: "/members" });
          }
        });
      }
    },
  },
};
</script>

<style>
label {
  margin-right: 15px;
  margin-left: 15px;
}
button {
  margin-bottom: 20px;
}
</style>