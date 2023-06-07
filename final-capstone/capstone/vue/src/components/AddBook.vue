<template>
  <div class="add-book">
    <form v-on:submit.prevent="getBook">
      <h1>Add Book</h1>
      <div>
        <label for="title">Title</label>
        <input
          type="radio"
          name="titleOrIsbn"
          id="title-button"
          v-model="selectedOption"
          value="title"
        />
        <label for="isbn">ISBN</label>
        <input
          type="radio"
          name="titleOrIsbn"
          id="isbn-button"
          v-model="selectedOption"
          value="isbn"
        />
      </div>
      <div v-if="selectedOption === 'title'">
        <label for="title-input">Title</label>
        <input type="text" id="title-input" v-model="bookToSearch.searchTerm" />
      </div>
      <div v-if="selectedOption === 'isbn'">
        <label for="isbn-input">ISBN</label>
        <input type="text" id="isbn-input" v-model="bookToSearch.searchTerm" />
      </div>
      <button type="submit" v-on:click="makeVisible">Find Book</button>
    </form>
    <div v-show="showResults" class="results-grid">
      <book-detail
        v-for="book in this.$store.state.bookResults"
        v-bind:showButton="true"
        v-bind:key="book.isbn"
        v-bind:book="book"
        class="book-card"
      />
    </div>
  </div>
</template>

<script>
import bookService from "../services/BookService";
import BookDetail from "./BookDetail.vue";

export default {
  components: { BookDetail },
  name: "addbook",
  data() {
    return {
      showResults: false,
      isbn: 0,
      selectedOption: "isbn",
      bookToSearch: {
        searchTerm: "",
      },
      bookResults: [],
    };
  },
  methods: {
    getBook() {
      this.resetBookResults();
      const searchTerm = this.bookToSearch.searchTerm;
      if (this.selectedOption == "isbn") {
        bookService.get(searchTerm).then((response) => {
          if (response.status === 200) {
            this.bookResults = [response.data];
            this.$store.commit("ADD_BOOK_RESULTS", this.bookResults);
            this.resetBookResults();
          }
        });
      } else {
        bookService.listBooksByTitle(searchTerm).then((response) => {
          if (response.status === 200) {
            this.bookResults = response.data;
            this.$store.commit("ADD_BOOK_RESULTS", this.bookResults);
            this.resetBookResults();
          }
        });
      }
    },
    resetBookResults() {
      this.bookResults = [];
    },
    makeVisible() {
      this.showResults = true;
    },
    addToReading(isbn) {
      bookService.addToReadingList(isbn).then((response) => {
        if (response.status === 200) {
          const username = this.$store.state.user.username;
          this.$router.push({ name: "user-profile", params: { username } });
        }
      });
    },
  },
};
</script>

<style scoped>
.add-book {
  padding: 15px;
  text-align: center;
  background: rgba(254, 209, 113, 0.5);
}

.results-grid {
  margin-left: 12%;
  margin-right: 12%;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  background-color: rgb(254 209 113 / 56%);
  gap: 30px;
  padding: 30px;
}

/* .book-card {
  padding: 1px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
} */
</style>