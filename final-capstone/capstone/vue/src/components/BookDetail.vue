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
    <div id="add-mark-buttons">
    <button class="add-button"
      v-if="$route.name == 'reading'"
      v-on:click="addToReading(book.isbn)"
    >
      Add to reading list
    </button>
    <button class="finished-button" v-else v-on:click="markBookAsRead(book.isbn)">
      Mark as finished
    </button>
    </div>
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
  padding-top:5%;
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

#add-mark-buttons {
  display: flex;
  justify-content:center;
}

.finished-button {
  transition-duration: 0.4s;
  width:200px;
  height:60px;
  padding: 12px 28px;
  border: 1px solid rgb(0, 0, 0);
  margin: 50px 30px 40px 30px;
  background-color: rgb(231, 247, 217);
  font-family: "Montserrat", sans-serif;
  color: rgb(48, 46, 49);
  font-weight: bold;
  font-size:15px ;
}

.finished-button:hover {
  background-color: rgb(115, 147, 126);
}

.add-button {
  transition-duration: 0.4s;
  width:200px;
  height:60px;
  padding: 12px 28px;
  border: 1px solid rgb(0, 0, 0);
  margin: 50px 30px 40px 30px;
  background-color: rgb(244, 252, 172);
  font-family: "Montserrat", sans-serif;
  color: rgb(48, 46, 49);
  font-weight: bold;
  font-size:15px ;
}

.add-button:hover {
  background-color: rgb(255, 191, 16);;
}
</style>
