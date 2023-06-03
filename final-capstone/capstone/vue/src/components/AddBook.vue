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
          <input type="text" id="title-input" v-model="bookToSearch.title" />
        </div>
        <div class="form-input-group" v-if="selectedOption === 'isbn'">
          <label for="isbn-input">ISBN</label>
          <input type="text" id="isbn-input" v-model="bookToSearch.isbn" />
        </div>
        <button type="submit" v-on:click="makeVisible" v-on:submit.prevent="getBookISBN" v-if="selectedOption === 'isbn'">Find Book By ISBN</button>
        <button type="submit" v-on:click="makeVisible" v-on:submit.prevent="getBookTitle" v-if="selectedOption === 'title'">Find Book By Title</button>
      </form>
    </div>
  <div v-show="showResults">
    <ul v-for="book in bookResults" v-bind:key="book.isbn">
      <li>
        <h3>{{book.title}}</h3>
        <p>By {{author}}, pages: {{book.numPages}}, ISBN: {{book.isbn}}</p>
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
        console.log("hi this is the console")
        console.log(this.bookToSearch.isbn)
        .then(response => {
          console.log(response.data)
          console.log(response.status)
          if (response.status === 200){
            this.bookResults.push(response.data);
          }
        })
    },
    getBookTitle() {
      
    },
    makeVisible(){
      this.showResults = true;
    }
  }
};
</script>

<style scoped>
</style>
