<template>
  <div>
    <h1 class="logo">BookBound Dragon Here</h1>
    <h2 class="username">{{$route.params.username}}</h2><!-- Changes with each user -->

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

<style>
@import url("https://fonts.cdnfonts.com/css/socake");
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");
.addReadingActivity{
  background: rgb(115, 147, 126);
}
.form-input-group-reading{
background: rgb(149, 151, 149);
}
.headerForActivity{
  background: rgb(115, 147, 126);
}
.total-books-read{
  background: rgb(115, 147, 126);
}
.total-minutes-read{
   background: rgb(115, 147, 126);
}
.minute-total{
  background: rgb(149, 151, 149);
}
.book-total{
   background: rgb(149, 151, 149);
}
.reading-activity{
   background: rgb(115, 147, 126);
}
.activity-list{
   background: rgb(149, 151, 149);
}
#reading-list{
  background: rgb(149, 151, 149);
}
.logo{
  background:rgb(115, 147, 126);
}
.username{
 background: rgb(115, 147, 126);
}
</style>