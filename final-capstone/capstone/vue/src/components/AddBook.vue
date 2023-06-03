<template>
  <div>
    <div id="add" class="text-center">
      <form>
        <h1>Add Book</h1>
        <div>
          <label for="title">Title</label>
          <input type="radio" name="titleOrIsbn" id="title-button" v-model="selectedOption" value="title" />
          <label for="isbn">ISBN</label>
          <input type="radio" name="titleOrIsbn" id="isbn-button" v-model="selectedOption" value="isbn" />
        </div>
        <div class="form-input-group" v-if="selectedOption === 'title'">
          <label for="title-input">Title</label>
          <input type="text" id="title-input" v-model="book.title" />
        </div>
        <div class="form-input-group" v-if="selectedOption === 'isbn'">
          <label for="isbn-input">ISBN</label>
          <input type="text" id="isbn-input" v-model="book.isbn" />
        </div>
        <button type="submit" v-on:submit.prevent="getBookISBN" v-if="selectedOption === 'isbn'">Find Book By ISBN</button>
        <button type="submit" v-on:submit.prevent="getBookTitle" v-if="selectedOption === 'title'">Find Book By Title</button>
      </form>
    </div>
  <!-- <div>
    <ul v-for="book in bookResults" v-bind:key="book.isbn">
      <li>
        <h3>{{book.title}}</h3>
        <p>By {{author}}, pages: {{book.numPages}}, ISBN: {{book.isbn}}</p>
      </li>
    </ul>
  </div> -->
  </div>
</template>

<script>
import bookService from "../services/BookService";

export default {
  name: "addbook",
  data() {
    return {
      selectedOption: "isbn",
      bookToSearch: {
        title: "",
        isbn: "",
      },
      bookResults: [{
        title: "",
        isbn: "",
        author: "",
        numPages: 0,
      }]
    };
  },
  methods: {
    getBookISBN() {
      bookService
        .get(this.bookToSearch.isbn)
        .then(response => {
          if (response.status === 200){
            this.bookResults.push(response.data);
          }
        })
    },
    getBookTitle() {
      
    }
  },
};
</script>

<style scoped>
</style>
