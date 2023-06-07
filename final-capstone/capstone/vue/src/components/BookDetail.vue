<template>
  <div>
      <img :src="book.coverUrl">
      <h4>{{book.title}}</h4>
      <p>Author: {{book.author}}</p>
      <p>ISBN: {{book.isbn}}</p>
      <p>Pages: {{book.numPages}}</p>
      <p>Publisher: {{book.publisher}}</p>
      <button v-if="$route.name == 'reading'" v-on:click="addToReading(book.isbn)">Add to reading list</button>
      <button v-else v-on:click="markBookAsRead(book.isbn)">Mark as finished</button>
      <br>
  </div>
</template>

<script>
import bookService from "../services/BookService";
import readingListService from "../services/ReadingListService";
export default {
  name: "book-detail",
  props: {
    book:  Object
  },
  methods: {
    markBookAsRead(isbn) {
      readingListService
        .markBookAsFinished(this.$route.params.username, isbn)
        .then((response)=> {
          if (response.status == 200) {
            this.reloadPage();
          }
        })
    },
    reloadPage() {
      window.location.reload();
    },
      addToReading(isbn){
        const username = this.$store.state.user.username;
        bookService
          .addToReadingList(isbn, username)
          .then((response)=> {
            if (response.status === 200) {
                this.$router.push({ name: 'user-profile', params: { username } });
                          
            }
          })
      }
  }
}
</script>
