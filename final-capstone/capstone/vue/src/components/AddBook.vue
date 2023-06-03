<template>
  <div>
    <div id="add" class="text-center">
      <form v-on:submit.prevent="getBook">
        <h1>Add Book</h1>
       <!-- <div>
        <label for ="titleOrIsbn">Switch ISBN or Title</label>
         <input type="checkbox" name="titleOrIsbn" id="isbn-button" v-on:change="switchLookup()">
      </div> -->
        <!-- <div class="form-input-group">
          <label for="title">Title</label>
          <input 
            type="text" 
            id="title" 
            v-model="book.title"/>
        </div> -->
        <div class="form-input-group">
          <label for="isbn">ISBN</label>
          <input
            type="number"
            id="isbn"
            v-model="book.isbn"/>
        </div>
        <button type="submit">Find Book</button>
      </form>
    </div>
  <div>
    <ul></ul>
  </div>
  </div>
</template>

<script>
import bookService from "../services/BookService";

export default {
  name: "addbook",
  data() {
    return {
      book: {
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
    getBook(){
      bookService
        .get(this.book.isbn)
        .

    }
    // addBook() {
    //   const newBook = {
    //     title: this.book.title,
    //     isbn: this.book.isbn,
    //   };
    //   if (this.username != "") {
    //     bookService.add(newBook).then((response) => {
    //       if (response.status === 200) {
    //         this.book = {
    //           title: "",
    //           isbn: "",
    //         };
    //         this.$router.push({path: "/members"});
    //       }
    //     });
    //   }
    // },
  },
};
</script>

<style scoped>

</style>