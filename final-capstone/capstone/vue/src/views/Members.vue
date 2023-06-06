<template>
  <div class="members">
    <div class="loading" v-if="isLoading">
      <img src="../assets/book_pages_opening.gif" />
    </div>
    <h2>BookBound Image Here</h2>
    <div id="nav">
      <router-link v-bind:to="{ name: 'reading' }"> Reading | </router-link>
      <router-link v-bind:to="{ name: 'prizes' }"> Prizes | </router-link>
      <router-link v-bind:to="{ name: 'settings' }"> Settings </router-link>
    </div>
    <h3 id="member-heading">Your Family Members</h3>
    <div class="button">
      <router-link to="/addmember">
        <button>Add Family Member</button>
      </router-link>
    </div>
    <ul v-for="member in members" v-bind:key="member.id">
      <li>
        <router-link
          v-bind:to="{
            name: 'user-profile',
            params: { username: member.username },
          }"
          >{{ member.username }}</router-link
        >
      </li>
    </ul>
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

<style>
@import url("https://fonts.googleapis.com/css2?family=Libre+Baskerville&display=swap%27");

.members {
  background: linear-gradient(
    to right,
    rgba(122, 85, 85, 0.3),
    rgba(255, 165, 0, 0.3),
    rgba(255, 255, 0, 0.3),
    rgba(50, 205, 50, 0.3),
    rgba(0, 191, 255, 0.3),
    rgba(148, 0, 211, 0.3)
  );
  color: rgb(88, 85, 99);
  font-family: "Libre Baskerville";
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
</style>