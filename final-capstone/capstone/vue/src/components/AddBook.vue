<template>
  <div>
    <div id="add" class="text-center">
      <form v-on:submit.prevent="addForm">
        <h1>Add Book</h1>
        <div class="form-input-group">
          <label for="title">Title</label>
          <input type="text" id="title" v-model="book.title" required />
        </div>
        <div class="form-input-group">
          <label for="isbn">ISBN</label>
          <input
            type="password"
            id="password"
            v-model="user.password"
            required
          />
        </div>
        <button type="submit">Create Book</button>
      </form>
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
    };
  },
  methods: {
    addBook() {
      const newBook = {
        title: this.book.title,
        isbn: this.book.isbn,
      };
      if (this.username != "") {
        bookService.add(newBook).then((response) => {
          if (response.status === 200) {
            this.book = {
              title: "",
              isbn: "",
            };
            this.$router.push({path: "/members"});
          }
        });
      }
    },
  },
};
</script>

<style>
</style>