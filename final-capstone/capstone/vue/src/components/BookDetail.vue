<template>
  <div class="book-card">
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
    <button v-else v-on:click="markBookAsRead(book.isbn)">
      Mark as finished
    </button>
    <br />
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
  justify-content: center;
  background: rgba(254, 209, 113, 0.623);
  padding: 15px;
  border-radius: 3px;
  border-style: solid;
  border-color: rgb(187, 12, 12);
  border-radius: 2%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.book-image {
  text-align: center;
  margin-bottom: 10px;
}

.book-image img {
  max-width: 100%;
  height: auto;
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

.book-details button {
  margin-top: 10px;
}
</style>
