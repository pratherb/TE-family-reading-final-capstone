<template>
  <div>
    <!-- <img :src="prize.itemUrl"> -->
    <h4>Prizes & Requirements</h4>
    <p>Prize: {{ prizes.prizeName }}</p>
    <p>Description: {{ prizes.description }}</p>
    <p>Milestone: {{ prizes.milestone }}</p>
    <p>User Group: {{ prizes.userGroup }}</p>
    <p>Max Prizes: {{ prizes.maxPrizes }}</p>
    <p>Start Date: {{ prizes.startDate }}</p>
    <p>End Date: {{ prizes.endDate }}</p>
    <button v-if="showButton" v-on:click="addToPrizes(prize.prizeName)">
      Add to Prize List
    </button>
  </div>
</template>

<script>
import prizeService from "../services/PrizeService";
export default {
  name: "prize-detail",
  props: {
    prize: Object,
    showButton: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    addToPrizes(prize) {
      prizeService.addToPrizeList(prize).then((response) => {
        if (response.status === 200) {
          const username = this.$store.state.user.username;
          this.$router.push({ name: "user-profile", params: { username } });
        }
      });
    },
  },
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Libre+Baskerville&display=swap%27");

.members {
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

li {
  list-style-type: none;
  text-align: center;
}
</style>