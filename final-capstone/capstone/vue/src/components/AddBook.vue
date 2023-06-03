<template>
  <div>
    <div id="add" class="text-center">
      <form v-on:submit.prevent="addForm">
        <h1>Add Book</h1>
       <div>
         <label for ="title">Title</label>
         <input type="radio" name="titleOrIsbn" id="title-button">
        <label for ="isbn">ISBN</label>
         <input type="radio" name="titleOrIsbn" id="isbn-button">
      </div>
        <div class="form-input-group" v-if="searchTitle">
          <label for="title">Title</label>
          <input 
            type="text" 
            id="title" 
            v-model="book.title"/>
        </div>
        <div class="form-input-group" v-if="searchIsbn">
          <label for="isbn">ISBN</label>
          <input
            type="number"
            id="isbn"
            v-model="book.isbn"/>
        </div>
        <button type="submit">Create Book</button>
      </form>
    </div>
  </div>
</template>

<script>
// import bookService from "../services/BookService";

export default {
  name: "addbook",
  data() {
    return {
      searchIsbn: false,
      seachTitle: false,
      book: {
        title: "",
        isbn: "",
      },
    };
  },
  methods: {
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