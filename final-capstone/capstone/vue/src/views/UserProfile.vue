<template>
  <div>
    <h1>BookBound Dragon Here</h1>
    <h2>{{$route.params.username}}</h2><!-- Changes with each user -->

    <reading-totals/>    
    <h3 id="reading-list">Currently Reading</h3>

    <div class="loading" v-if="isLoading">
      <img src="../assets/book_pages_opening.gif" />
    </div>

    <div v-else>
    <div>
      <ul>
        <li>
        <book-detail v-for="book in readingList" :key="book.isbn" :book="book" />
          <!-- <h4>{{book.title}}</h4>
          <p>Author: {{book.author}}</p>
          <p>ISBN: {{book.isbn}}</p>
          <p>Pages: {{book.numPages}}</p> -->
        </li>
      </ul>
    </div>
    </div>
    <div>
    <reading-activity/>
    </div>

  </div>
</template>

<script>
import ReadingActivity from '../components/ReadingActivity.vue';
import ReadingListService from '../services/ReadingListService';
import BookDetail from '../components/BookDetail.vue'
import ReadingTotals from '../components/ReadingTotals.vue';
export default {
  components: { ReadingActivity, BookDetail, ReadingTotals },
  name: "user-profile",
  data() {
    return {
      isLoading: true,
      readingList: [] 
    }
  },
  created () {
    this.addReading();
  },
  methods: {
    addReading() {
      ReadingListService
      .getUserReadingList(this.$route.params.username)
      .then((response)=>{
        this.readingList = response.data;
        this.isLoading = false;
      })
    }
  }
};
</script>

<style scoped>
@import url("https://fonts.cdnfonts.com/css/socake");
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");

.members {

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
</style>