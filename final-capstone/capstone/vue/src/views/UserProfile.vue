<template>
  <div class="users">
    <div class="user-head">
      <h2 class="usersname">{{ $route.params.username }}</h2>
      <p class="page">'s Page</p>
      <!-- Changes with each user -->
      <h1>
        <router-link v-bind:to="{ name: 'home' }">
          <img
            class="logo-user"
            src="../images/sharpened-transparent-logo.png"
            alt="logo"
          />
        </router-link>
      </h1>
    </div>

    <div>
      <reading-totals class="reading-totals" />
          <h3 id="currently-reading-header">Currently Reading</h3>
    </div>
    <div class="reading-list">
      <div class="loading" v-if="isLoading">
        <img src="../assets/book_pages_opening.gif" />
      </div>

      <div v-else class="reading-list-grid">
        <book-detail
          v-for="book in readingList"
          :key="book.isbn"
          :book="book"
          class="book-card"
        />
      </div>
    </div>
    <div>
      <reading-activity />
    </div>
  </div>
</template>

<script>
import ReadingActivity from "../components/ReadingActivity.vue";
import ReadingListService from "../services/ReadingListService";
import BookDetail from "../components/BookDetail.vue";
import ReadingTotals from "../components/ReadingTotals.vue";
export default {
  components: { ReadingActivity, BookDetail, ReadingTotals },
  name: "user-profile",
  data() {
    return {
      isLoading: true,
      readingList: [],
    };
  },
  created() {
    this.addReading();
  },
  methods: {
    addReading() {
      ReadingListService.getUserReadingList(this.$route.params.username).then(
        (response) => {
          this.readingList = response.data;
          this.isLoading = false;
        }
      );
    },
  },
};
</script>

<style>
@import url("https://fonts.cdnfonts.com/css/socake");
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");
.page {
  font-family: "Socake", sans-serif;
  color: rgb(88, 85, 91);
  font-size: 50px;
}
.users {
  background: rgba(254, 209, 113, 0.5);
}
.addReadingActivity {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  background: rgba(232, 123, 31, 0.8);
  padding-top: 30px;
  width: 900px;
  height: 350px;
  justify-content: space-between;
  border-radius: 8px;
  border: solid 3px rgb(182, 90, 10);
  font-family: "Socake", sans-serif;
  color: rgb(88, 85, 91);
  font-size: 20px;
}
*/ .reading-totals {
  background-color: rgba(254, 209, 113, 0.5);
  padding: 5px;
  margin-left: 20%;
  margin-right: 20%;
}

.reading-list {
  /* background-color: rgba(254, 209, 113, 0.5); */
  color: rgb(88, 85, 99);
  text-align: center;
  /* margin-left: 15%;
  margin-right: 15%; */
  padding: 15px;
  font-family: "Montserrat", sans-serif;
}

.reading-list-grid {
  margin-left: 12%;
  margin-right: 12%;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  background-color: rgb(254 209 113 / 56%);
  gap: 30px;
  padding: 30px;
}

.totals {
  margin-bottom: 0;
  display: grid;
}

#currently-reading-header{
    background: rgb(115, 147, 126);
  grid-row: 1;
  grid-column: 1;
  color: rgb(88, 85, 91);
  font-family: "Socake", sans-serif;
  font-size: 30px;
  border-radius: 30px;
  height: 80px;
  text-align: center;
  padding-top: 50px;
  width: 350px;
  margin: 40px auto;
  border: 3px solid rgb(77, 97, 84);

}
#title-books-read {
  background: rgb(115, 147, 126);
  grid-row: 1;
  grid-column: 3;
  color: rgb(88, 85, 91);
  font-family: "Socake", sans-serif;
  font-size: 30px;
  border-radius: 30px;
  height: 80px;
  text-align: center;
  padding-top: 50px;
  width: 350px;
  margin: 40px auto;
  border: 3px solid rgb(77, 97, 84);
}
#title-mins {
  background: rgb(115, 147, 126);
  grid-row: 1;
  grid-column: 1;
  color: rgb(88, 85, 91);
  font-family: "Socake", sans-serif;
  font-size: 30px;
  border-radius: 30px;
  height: 80px;
  text-align: center;
  padding-top: 50px;
  width: 350px;
  margin: 40px auto;
  border: 3px solid rgb(77, 97, 84);
}
#number-mins {
  height: 60px;
  background: rgb(115, 147, 126);
  grid-row: 2;
  grid-column: 1;
  color: rgb(88, 85, 91);
  font-family: "Montserrat", sans-serif;
  font-size: 40px;
  border-radius: 25px;
  text-align: center;
  padding-top: 25px;
  width: 200px;
  margin: 0 auto 20px auto;
  border: 3px solid rgb(77, 97, 84);
}
#number-books-read {
  background: rgb(115, 147, 126);
  grid-row: 2;
  grid-column: 3;
  color: rgb(88, 85, 91);
  font-family: "Montserrat", sans-serif;
  font-size: 40px;
  border-radius: 30px;
  text-align: center;
  padding-top: 25px;
  width: 200px;
  margin: 0 auto 20px auto;
  border: 3px solid rgb(77, 97, 84);
}

.usersname {
  font-family: "Socake", sans-serif;
  color: rgb(88, 85, 91);
  font-size: 50px;
}
.user-head {
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
ul {
  list-style: none;
}
.logo-user {
  width: 300px;
  height: 350px;
  padding-top: 30px;
  margin-left: 15px;
}
.activity-button {
  transition-duration: 0.4s;
  width: 200px;
  height: 60px;
  padding: 12px 28px;
  border: 1px solid rgb(0, 0, 0);
  margin: 50px 30px 40px 30px;
  background-color: rgb(244, 252, 172);
  font-family: "Montserrat", sans-serif;
  color: rgb(48, 46, 49);
  font-weight: bold;
  font-size: 15px;
}
.activity-button:hover {
  background-color: rgb(255, 191, 16);
}

</style>