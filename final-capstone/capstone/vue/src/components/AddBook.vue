<template>
  <div>
    <div id="add" class="text-center">
      <form v-on:submit.prevent="addForm">
        <h1>Add Book</h1>
        <div>
          <label for="title">Title</label>
          <input type="radio" name="titleOrIsbn" id="title-button" v-model="selectedOption" value="title" />
          <label for="isbn">ISBN</label>
          <input type="radio" name="titleOrIsbn" id="isbn-button" v-model="selectedOption" value="isbn" />
        </div>
        <div class="form-input-group" v-if="selectedOption === 'title'">
          <label for="title-input">Title</label>
          <input type="text" id="title-input" v-model="book.title" />
        </div>
        <div class="form-input-group" v-if="selectedOption === 'isbn'">
          <label for="isbn-input">ISBN</label>
          <input type="number" id="isbn-input" v-model="book.isbn" />
        </div>
        <button type="submit">Create Book</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "addbook",
  data() {
    return {
      selectedOption: "isbn",
      book: {
        title: "",
        isbn: "",
      },
    };
  },
  methods: {
    addForm() {
      // Handle form submission
    },
  },
};
</script>

<style scoped>

</style>
