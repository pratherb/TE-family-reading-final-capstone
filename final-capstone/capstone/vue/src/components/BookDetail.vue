<template>
  <div>
      <h4>{{book.title}}</h4>
      <p>Author: {{book.author}}</p>
      <p>ISBN: {{book.isbn}}</p>
      <p>Pages: {{book.numPages}}</p>
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
              this.$router.push({name: 'members'});
            
            }
          })
      }
  }
}
</script>

<style>

</style>