<template>
  <div>
      <div>
      <br>
      <br>
      <h3>Reading Activity</h3>
      <ul v-for="activity in activityList" v-bind:key="activity.id">
          <li>
              <p>User: {{$route.params.username}}</p>
              <p>ISBN: {{activity.bookIsbn}}</p>
              <p>Minutes of reading: {{activity.minutesRead}}</p>
              <p>Format: {{activity.format}}</p>
              <p>Notes: {{activity.notes}}</p>
              <br>
          </li>
      </ul>
      </div>
      <br>
      <br>
      <div>
          <form v-on:submit.prevent="addActivity">
            <h1>Add Reading Activity</h1>
            <div class="form-input-group">
                <label for="username">Username</label>
                <input type="text" id="username" v-model="newActivity.username" required />
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
import BookService from '../services/BookService';
export default {
    name: "activity",
    data() {
       return {
        isLoading: true,
        activityList: [],
        newActivity: {
            username: "",
            bookIsbn: "",
            minutesRead: 0,
            format: "",
            notes: ""
        } 
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
          const username = this.$route.params.username;
          activityService
            .create(this.newActivity, username)
            .then((response)=>{
                if (response.status == 200){
                    this.clearNewActivity();
                    this.$router.push({ name: 'user-profile', params: {username:username} });
                }
            })
            this.$router.push({ name: 'user-profile', params: {username:username} });
      },
      clearNewActivity() {
          this.newActivity.username = "";
          this.newActivity.bookIsbn = "",
          this.newActivity.minutesRead = 0;
          this.newActivity.format = "";
          this.newActivity.notes = "";
      },
      deleteBook(isbn) {
          BookService
            .delete(isbn)
            .then(response => {
                if (response.status === 200) {
                    this.getUserReadingActivity
                }
            })
      }
  }
}
</script>

<style>

</style>