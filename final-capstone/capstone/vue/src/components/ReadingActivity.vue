<template>
  <div>
      <ul v-for="activity in activityList" v-bind:key="activity.id">
          <li>
              <p>User: {{this.$route.params.username}}</p>
              <p>ISBN: {{activity.bookIsbn}}</p>
              <p>Minutes of reading: {{activity.minutesRead}}</p>
              <p>Format: {{activity.format}}</p>
              <p>Notes: {{activity.notes}}</p>
          </li>
      </ul>
      <div>
          <form v-on:submit.prevent="addActivity">
            <h1>Add Reading Activity</h1>
            <div class="form-input-group">
                <label for="username">Username</label>
                <input type="number" id="username" v-model="newActivity.username" required />
            </div>
            <div class="form-input-group">
                <label for="bookIsbn">Book ISBN</label>
                <input type="text" id="bookIsbn" v-model="newActivity.bookIsbn" required />
            </div>
            <div class="form-input-group">
                <label for="minutesRead">Minutes Read</label>
                <input type="number" id="minutesRead" v-model="newActivity.minutesRead" required />
            </div>
            <div class="form-input-group">
                <label for="format">Format</label>
                <input type="text" id="format" v-model="newActivity.format" required />
            </div>
            <div class="form-input-group">
                <label for="notes">Notes</label>
                <input type="text" id="notes" v-model="newActivity.notes"/>
            </div>
            <button type="submit">Add Activity</button>
          </form>
      </div>
    </div>
</template>

<script>
import activityService from '../services/ActivityService';
export default {
    name: "activity",
    newActivity: {
        username: "",
        bookIsbn: "",
        minutesRead: 0,
        format: "",
        notes: ""
    },
    data() {
       return {
        isLoading: true,
        activityList: [] 
    }
  },
  created() {
      activityService
        .getUserReadingActivity(this.$route.params.username)
        .then((response)=>{
            this.activityList = response.data;
            this.isLoading = false;
        })
  },
  methods: {
      addActivity(){
          activityService
            .create(this.newActivity, this.$route.params.username)
            .then((response)=>{
                if (response.status == 201){
                    this.clearNewActivity();
                }
            })
      },
      clearNewActivity() {
          this.newActivity.username = "";
          this.newActivity.bookIsbn = "",
          this.newActivity.minutesRead = 0;
          this.newActivity.format = "";
          this.newActivity.notes = "";
      }
  }
}
</script>

<style>

</style>