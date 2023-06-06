<template>
  <div>
    <!-- <img :src="prize.itemUrl"> -->
    <h4>Prizes & Requirements</h4>
    <p>Prize: {{ prize.prizeName }}</p>
    <p>Description: {{ prize.description }}</p>
    <p>Milestone: {{ prize.milestone }}</p>
    <p>User Group: {{ prize.userGroup }}</p>
    <p>Max Prizes: {{ prize.maxPrizes }}</p>
    <p>Start Date: {{ prize.startDate }}</p>
    <p>End Date: {{ prize.endDate }}</p>
  </div>
</template>

<script>
import prizeService from "../services/PrizeService";
export default {
  name: "prize-detail",
  props: {
    prize: Object,
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