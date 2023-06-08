<template>
  <div class="activity-page">
    <div class="activity-log">
      <br />
      <br />
      <h3 class="reading-activity">Reading Activity</h3>
      <div class="list-boxes">
        <ul
          class="activity-list"
          v-for="activity in activityList"
          v-bind:key="activity.id"
        >
          <li>
            <p>User: {{ $route.params.username }}</p>
            <p>ISBN: {{ activity.bookIsbn }}</p>
            <p>Minutes of reading: {{ activity.minutesRead }}</p>
            <p>Format: {{ activity.format }}</p>
            <p>Notes: {{ activity.notes }}</p>
            <br />
          </li>
        </ul>
      </div>
    </div>
    <br />
    <br />
    <div class="add-activity">
      <form class="addReadingActivity" v-on:submit.prevent="addActivity">
        <h1 class="headerForActivity">Add Reading Activity</h1>
        <div class="form-input-group-reading" v-show="isParent">
          <label for="username">Username</label>
          <input type="text" id="username" v-model="newActivity.username" />
        </div>
        <div class="form-input-group-reading">
          <label for="bookIsbn">Book ISBN</label>
          <input
            type="text"
            id="bookIsbn"
            v-model="newActivity.bookIsbn"
            required
          />
        </div>
        <div class="form-input-group-reading">
          <label for="minutesRead">Minutes Read</label>
          <input
            type="number"
            id="minutesRead"
            v-model="newActivity.minutesRead"
            required
          />
        </div>
        <div class="form-input-group-reading">
          <label for="format">Format</label>
          <input
            type="text"
            id="format"
            v-model="newActivity.format"
            required
          />
        </div>
        <div class="form-input-group-reading">
          <label for="notes">Notes</label>
          <input type="text" id="notes" v-model="newActivity.notes" />
        </div>
        <button class="activity-button" type="submit">Add Activity</button>
      </form>
    </div>
  </div>
</template>

<script>
import activityService from "../services/ActivityService";
export default {
  name: "activity",
  data() {
    return {
      isLoading: true,
      activityList: [],
      newActivity: {
        username: this.$store.state.user.username,
        bookIsbn: "",
        minutesRead: 0,
        format: "",
        notes: "",
      },
    };
  },
  computed: {
    isParent: function () {
      return this.$store.state.user.authorities[0].name != "ROLE_CHILD";
    },
  },
  created() {
    activityService
      .getUserReadingActivity(this.$route.params.username)
      .then((response) => {
        this.activityList = response.data;
        this.isLoading = false;
      });
  },
  methods: {
    addActivity() {
      const username = this.$route.params.username;
      activityService.create(this.newActivity, username).then((response) => {
        if (response.status == 200) {
          this.clearNewActivity();
          this.reloadPage();
        }
      });
    },
    clearNewActivity() {
      this.newActivity.username = "";
      (this.newActivity.bookIsbn = ""), (this.newActivity.minutesRead = 0);
      this.newActivity.format = "";
      this.newActivity.notes = "";
    },
    reloadPage() {
      window.location.reload();
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.cdnfonts.com/css/socake");
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");

.activity-page {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: space-between;
}

.reading-activity {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-family: "Socake", sans-serif;
  font-size: 35px;
  color: rgb(88, 85, 91);
  background: rgba(115, 147, 126, 0.7);
  border: solid 3px;
  border-color: rgb(115, 147, 126);
  border-radius: 4px;
  height: 45px;
  width: 300px;
  margin: 0 auto;
  margin-bottom: 45px;
}

.activity-list {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  font-family: "Montserrat", sans-serif;
  font-size: 15px;
  flex-wrap: wrap;
  font-weight: 900;
  color: rgb(30 29 30);
  background: rgb(241 106 106);
  border: solid 3px;
  border-color: rgb(209 20 20);
  border-radius: 4px;
  width: 230px;
}

.list-boxes {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  justify-content: space-evenly;
  margin-bottom: 100px;
}

</style>