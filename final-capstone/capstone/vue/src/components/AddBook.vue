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
          <input type="text" id="title-input" v-model="bookToSearch.searchTerm" />
        </div>
        <div class="form-input-group" v-if="selectedOption === 'isbn'">
          <label for="isbn-input">ISBN</label>
          <input type="text" id="isbn-input" v-model="bookToSearch.searchTerm" />
        </div>
        <button type="submit" v-on:click="makeVisible">Find Book</button>
      </form>
    </div>
    <div v-show="showResults">
      <ul v-for="book in bookResults" v-bind:key="book.isbn">
        <li>
          <h3>{{ book.title }}</h3>
          <p>
            By {{ book.author }}, pages: {{ book.numPages }}, ISBN:
            {{ book.isbn }}
          </p>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import bookService from "../services/BookService";

export default {
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
          }
        });
      } else {
        bookService.listBooksByTitle(searchTerm).then((response) => {
          if (response.status === 200) {
            this.bookResults = response.data;
          }
        });
      }
    },
    makeVisible() {
      this.showResults = true;
    },
  },
};
</script>

<style scoped>

</style>
