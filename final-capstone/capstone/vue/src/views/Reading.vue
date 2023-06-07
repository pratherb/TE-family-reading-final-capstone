<template>
<div>
    <header>
      <div class="logo_header">
        <h3 id="member-heading">Book Search</h3>
        <router-link to="/">
        <img src="../images/sharpened-transparent-logo.png" />
        </router-link>
      </div>
      </header>
    <add-book/>
  </div>
</template>

<script>
import AddBook from "../components/AddBook.vue";

export default {
  name: "reading",
  components: {
    AddBook,
  },
  methods: {},
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Libre+Baskerville&display=swap%27");

.results {
  background: rgb(254, 209, 113);
  color: rgb(88, 85, 99);
  font-family: "Libre Baskerville";
  text-align: center;
  margin-left: 15%;
  margin-right: 15%;
  padding: 15px;
}

h2 {
  text-align: center;
  font-size: 48px;
}

h3 {
  font-size: 36px;
}

body {
  padding: 25px;
  background: rgba(254, 209, 113, 0.5);
}
</style>