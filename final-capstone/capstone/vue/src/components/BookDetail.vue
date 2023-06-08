<template>
  <div>
    <div class="book-image">
      <img :src="book.coverUrl" />
    </div>
    <div class="book-details">
      <h4>{{ book.title }}</h4>
      <p>Author: {{ book.author }}</p>
      <p>ISBN: {{ book.isbn }}</p>
      <p>Pages: {{ book.numPages }}</p>
      <p>Publisher: {{ book.publisher }}</p>
    </div>
    <button
      v-if="$route.name == 'reading'"
      v-on:click="addToReading(book.isbn)"
    >
      Add to reading list
    </button>
    <button
      v-else
      v-on:click="markBookAsRead(book.isbn)"
    >
      Mark as finished
    </button>
  </div>
</template>

<script>
import bookService from "../services/BookService";
import readingListService from "../services/ReadingListService";
export default {
  name: "book-detail",
  props: {
    book: Object,
  },
  methods: {
    markBookAsRead(isbn) {
      readingListService
        .markBookAsFinished(this.$route.params.username, isbn)
        .then((response) => {
          if (response.status == 200) {
            this.reloadPage();
          }
        });
    },
    reloadPage() {
      window.location.reload();
    },
    addToReading(isbn) {
      const username = this.$store.state.user.username;
      bookService.addToReadingList(isbn, username).then((response) => {
        if (response.status === 200) {
          this.$router.push({ name: "user-profile", params: { username } });
        }
      });
    },
  },
};
</script>
<style scoped>
.book-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  background: rgb(255 233 187);
  border-radius: 1px;
  /* border-style: solid;
  border-color: rgb(0, 0, 0);
  border-radius: 10%; */
  min-height: 550px;
  max-height: 550px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.658);
}

.book-image {
  text-align: center;
  margin-top: 10px;
  margin-bottom: 10px;
  overflow: hidden;
}

.book-image img {
  padding-top: 5%;
  max-height: 99%;
  max-width: 90%;
  border-radius: 8%;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.book-details {
  text-align: center;
}

.book-details h4 {
  margin-top: 0;
  margin-bottom: 5px;
}

.book-details p {
  margin: 0;
  margin-bottom: 5px;
}

button {
  margin-top: 25px;
}
</style>
