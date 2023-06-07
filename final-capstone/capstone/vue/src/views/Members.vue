<template>
  <div class="members">
    <div class="logo_header">
      <h3 id="member-heading">Your Family Members</h3>
      <img src="../images/sharpened-transparent-logo.png" />
    </div>
    <table class="members-table">
      <tr
        class="members-row"
        v-for="member in memberResults"
        v-bind:key="member.id"
      >
        <td class="members-table-data">
          <router-link
            class="username"
            v-bind:to="{
              name: 'user-profile',
              params: { username: member.username },
            }"
            >{{ member.username }}</router-link
          >
        </td>
      </tr>
    </table>
    <div class="grid-container">
      <family-reading-totals class="grid-item1" />
      <br />
      <the-leaderboard class="grid-item2" />
    </div>
    <div class="loading" v-if="isLoading">
      <img src="../assets/book_pages_opening.gif" />
    </div>
    <div id="nav">
      <button class="selections">
        <router-link v-bind:to="{ name: 'reading' }"> Reading</router-link>
      </button>
      <button class="selections">
        <router-link v-bind:to="{ name: 'prize' }"> Prizes </router-link>
      </button>
      <button class="selections" v-show="isParent">
        <router-link v-bind:to="{ name: 'addmember' }">Add User</router-link>
      </button>
    </div>
  </div>
</template>

<script >
import FamilyReadingTotals from "../components/FamilyReadingTotals.vue";
import TheLeaderboard from "../components/TheLeaderboard.vue";
import docsService from "../services/DocsService";

export default {
  name: "members",
  components: { FamilyReadingTotals, TheLeaderboard },
  data() {
    return {
      isLoading: true,
      errorMsg: "",
      filterText: "",
      memberResults: [],
    };
  },
  computed: {
    isParent: function () {
      return this.$store.state.user.authorities[0].name != "ROLE_CHILD";
    },
  },
  created() {
    docsService.list(this.$store.state.user.familyId).then((response) => {
      this.memberResults = response.data;
      this.isLoading = false;
    });
  },
};
</script>

<style>
@import url("https://fonts.cdnfonts.com/css/socake");
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");

.selections {
  transition-duration: 0.4s;
  padding: 12px 28px;
  border: 1px solid rgb(0, 0, 0);
  margin: 10px 15px 20px 10px;
  background-color: rgb(231, 247, 217);
}

.selections:hover {
  background-color: rgb(115, 147, 126);
}
.members {
  background: rgba(254, 209, 113, 0.5);
  text-align: center;
}

#member-heading {
  font-family: "Socake", sans-serif;
  color: rgb(88, 85, 91);
  font-size: 50px;
}

#nav a {
  color: rgb(88, 85, 91);
  text-decoration: none;
}
.members-table {
  display: flex;
  flex-direction: column;
  width: 30%;
  align-items: center;
  background: white;
  border: 2px solid;
  border-radius: 4px;
  margin: 20px auto 20px auto;
  /* top, right, bottom, left */
}
.members-row {
  background: white;
}
.members-data {
  border-bottom: solid 3px;
  border-color: black;
  width: 250px;
}
.username {
  font-family: "Montserrat", sans-serif;
  color: rgb(88, 85, 91);
  font-size: 30px;
  text-decoration: none;
}
.logo_header {
  border: solid 3px;
  border-color: rgb(115, 147, 126);
  background: rgba(115, 147, 126, 0.7);
  border-radius: 4px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 250px;
}

.logo_header img {
  width: 250px;
  height: 350px;
}
.loading {
  flex-direction: column;
  width: 200px;
}
#books-read {
  color: rgb(88, 85, 91);
  font-family: "Socake", sans-serif;
  font-size: 25px;
}
#all-books {
  color: rgb(88, 85, 91);
  font-family: "Montserrat", sans-serif;
  font-size: 20px;
}
#all-minutes-read {
  color: rgb(88, 85, 91);
  font-family: "Socake", sans-serif;
  font-size: 25px;
}
#total-minutes {
  color: rgb(88, 85, 91);
  font-family: "Montserrat", sans-serif;
  font-size: 20px;
}
.leaderboard-head {
  color: rgb(88, 85, 91);
  font-family: "Socake", sans-serif;
  font-size: 30px;
  margin-bottom: 0;
}
.score {
  list-style-type: none;
  color: rgb(88, 85, 91);
  font-family: "Montserrat", sans-serif;
  font-size: 25px;
  margin-bottom: 15px;
}
.grid-container {
  display: grid;
  grid-template-columns: auto auto;
  grid-gap: 10px;
}
.grid-item1 {
  grid-column: 1;
  grid-row: 1;
}
.grid-item2 {
  grid-column: 2;
  grid-row: 1;
}
</style>