<template>
  <div>
    <h1>BookBound Dragon Here</h1>
    <h2>{{$route.params.username}}</h2><!-- Changes with each user -->
    <h3 id="reading-list">Currently Reading</h3>
    <div>
      <ul v-for="book in books" v-bind:key="book.title">
        <li>
          {{ book.title }}
        </li>
      </ul>
      <div class="button">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "user-profile",
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Libre+Baskerville&display=swap%27");

.members {
  background: linear-gradient(
    to right,
    rgba(122, 85, 85, 0.3),
    rgba(255, 165, 0, 0.3),
    rgba(255, 255, 0, 0.3),
    rgba(50, 205, 50, 0.3),
    rgba(0, 191, 255, 0.3),
    rgba(148, 0, 211, 0.3)
  );
  color: rgb(88, 85, 99);
  font-family: "Libre Baskerville";
  text-align: center;
}

h2 {
  text-align: center;
  font-size: 48px;
}

h3 {
  font-size: 36px;
}
</style>