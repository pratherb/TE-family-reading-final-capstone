<template>
  <div>
    <h1>BookBound Dragon Here</h1>
    <h2>{{$route.params.username}}</h2><!-- Changes with each user -->
    <h3 id="reading-list">Currently Reading</h3>

    <div class="loading" v-if="isLoading">
      <img src="../assets/book_pages_opening.gif" />
    </div>

    <div v-else>
    <label for="finished">Show finished books only</label><br>
    <input type="checkbox" id="finished" name="finished" v-on:change="flipFinishedAndReload">
    <div>
      <ul v-for="book in readingList" v-bind:key="book.isbn">
        <li>
          <h4>{{book.title}}</h4>
          <p>Author: {{book.author}}</p>
          <p>ISBN: {{book.isbn}}</p>
          <p>Pages: {{book.numPages}}</p>
        </li>
      </ul>
    </div>
    </div>
    <div class="loading" v-if="isLoading">
      <img src="../assets/book_pages_opening.gif" />
    </div>
    <div>
    <reading-activity/>
    </div>

  </div>
</template>

<script>
import ReadingActivity from '../components/ReadingActivity.vue';
import ReadingListService from '../services/ReadingListService';
export default {
  components: { ReadingActivity },
  name: "user-profile",
  finished: false,
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
      .getUserReadingList(this.$route.params.username, this.finished)
      .then((response)=>{
        this.readingList = response.data;
        this.isLoading = false;
      })
    },
    flipFinishedAndReload() {
      if (this.finished == false){
        this.finished = true;
      } else {
        this.finished = false;
      }
      this.addReading();
    }
  }
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
</style>