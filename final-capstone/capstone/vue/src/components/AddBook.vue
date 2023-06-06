<template>
  <div>
    <div id="add" class="text-center">
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
        <div class="form-input-group" v-if="selectedOption === 'title'">
          <label for="title-input">Title</label>
          <input
            type="text"
            id="title-input"
            v-model="bookToSearch.searchTerm"
          />
        </div>
        <div class="form-input-group" v-if="selectedOption === 'isbn'">
          <label for="isbn-input">ISBN</label>
          <input
            type="text"
            id="isbn-input"
            v-model="bookToSearch.searchTerm"
          />
        </div>
        <button type="submit" v-on:click="makeVisible">Find Book</button>
      </form>
    </div>
    <div v-show="showResults">
      <ul>
        <li>
          <book-detail
            v-for="book in this.$store.state.bookResults"
            v-bind:showButton="true"
            v-bind:key="book.isbn"
            v-bind:book="book"
          />
        </li>
      </ul>
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
