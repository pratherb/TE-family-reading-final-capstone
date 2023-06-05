<template>
  <div>
      <img :src="book.coverUrl">
      <p>Author: {{book.author}}</p>
      <p>ISBN: {{book.isbn}}</p>
      <p>Pages: {{book.numPages}}</p>
      <p>Publisher: {{book.publisher}}</p>
      <button v-on:click="addToReading(book.isbn)">Add to reading list</button>
  </div>
</template>

<script>
import bookService from "../services/BookService";
export default {
  name: "book-detail",
  props: ['book'],
  methods: {
      addToReading(isbn){
        bookService
          .addToReadingList(isbn)
          .then((response)=> {
            if (response.status === 200) {
                const username = this.$store.state.user.username;
            this.$router.push({ name: 'user-profile', params: { username } });
                          
            }
          })
      }
  }
}
</script>

<style>

</style>