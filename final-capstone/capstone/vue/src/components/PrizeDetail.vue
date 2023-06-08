<template>
  <div class="prize-card">
    <!-- <img :src="prize.itemUrl"> -->
    <h3>{{ prize.name }}</h3>
    <h4>{{ prize.description }}</h4>
    <p>To win: read for {{ prize.milestone }} minutes</p>
    <p>Can be won by: {{ userGroupText }}</p>
    <p>Prizes remaining: {{ prize.maxPrizes }}</p>
    <p>Start Date: {{ prize.startDate }}</p>
    <p>End Date: {{ prize.endDate }}</p>
    <form v-on:submit.prevent="deletePrize(prize.name)" v-show="isParent">
      <button>Delete prize</button>
    </form>
  </div>
</template>

<script>
import prizeService from "../services/PrizeService";
export default {
  name: "prize-detail",
  props: ["prize"],
  computed: {
    userGroupText() {
      if (this.prize.userGroup === "both") {
        return "Parents and children";
      } else if (this.prize.userGroup === "parent") {
        return "Parents";
      } else if (this.prize.userGroup === "child") {
        return "Children";
      }
      return "";
    },
    isParent: function () {
      return this.$store.state.user.authorities[0].name != "ROLE_CHILD";
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
    deletePrize(prizeName) {
      prizeService.deletePrize(prizeName);
      window.location.reload();
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Libre+Baskerville&display=swap%27");
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");


.prize-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: rgb(255 233 187);
  border-radius: 1px;
  min-height: 350px;
  max-height: 350px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.658);
  font-family: "Montserrat", sans-serif;
}

p {
  margin-bottom: 10px;
  margin-top: 5px;
}

h3 {
  font-size: 32px;
  margin-bottom: 5px;
}

h4 {
  margin-top: 5px;
}

button {
  margin-top: 25px;
  font-family: sans-serif;
}
</style>