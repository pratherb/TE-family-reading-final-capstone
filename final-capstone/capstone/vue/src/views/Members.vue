<template>
  <div class="members">
    <div class="loading" v-if="isLoading">
      <img src="../assets/book_pages_opening.gif" />
    </div>
    <h2>BookBound Image Here</h2>
    <div id="nav">
     <button class="selections"> <router-link v-bind:to="{ name: 'reading' }"> Reading</router-link> </button>
       <button class="selections"><router-link v-bind:to="{ name: 'prizes' }"> Prizes </router-link></button>
       <button class="selections"><router-link v-bind:to="{ name: 'settings' }"> Settings </router-link></button>
    </div>
    <h3 id="member-heading">Your Family Members</h3>
  <table>
    <tr v-for="member in members" v-bind:key="member.id">
      <td>
        <router-link class="username"
          v-bind:to="{
            name: 'user-profile',
            params: { username: member.username },
          }"
          >{{ member.username }}</router-link
        >
      </td>
    </tr>
    </table>
    <div class="addButton">
      <router-link to="/addmember">
        <button>Add Family Member</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import docsService from "../services/DocsService";

export default {
  name: "members",
  components: {},
  data() {
    return {
      isLoading: true,
      errorMsg: "",
      filterText: "",
      members: [],
    };
  },
  /* computed: {
        filteredMembers() {
            return this.members.filter((member) => {
                return member.name.includes(this.filterText);
            })
        }
    }, */
  created() {
    docsService.list().then((response) => {
      this.members = response.data;
    });
  },
};
</script>

<style scoped>
@import url("https://fonts.cdnfonts.com/css/socake");
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");

#member-heading{
  font-family: "Socake", sans-serif;
  color: rgb(88, 85, 91);
}

.addButton{
  margin-top: 12px;
}
.selections {
  transition-duration: 0.4s;
  padding: 12px 28px;
  border: 1px solid rgb(0, 0, 0);
  margin: 10px;
  background: rgb(231, 247, 217);
}

.selections:hover {
  background-color: rgb(115, 147, 126);
}
.members {
  text-align: center;
}

h2 {
  text-align: center;
  font-size: 48px;
}

h3 {
  font-size: 36px;
}

li {
  list-style-type: none;
  text-align: center;
}

#nav a{
  color: black;
  text-decoration: none;
}
table{
  border: 1px solid;
}
table, th, td {
  margin: 0 auto;
}
.username{
  font-family: "Montserrat", sans-serif;
  text-decoration: underline;
  color: black;
  font-size: 20px;
}
</style>