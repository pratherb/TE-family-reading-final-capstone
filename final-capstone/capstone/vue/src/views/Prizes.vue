<template>
  <div>
    <header>
      <div class="logo_header">
        <h3 id="member-heading">Prizes</h3>
        <router-link to="/">
          <img src="../images/sharpened-transparent-logo.png" />
        </router-link>
      </div>
    </header>
    <div class="prizes">
      <div v-if="prizeList.length === 0" id="blank-list">
        <h3>Click below to add a new prize</h3>
      </div>
      <div v-else class="details">
        <prize-detail
          v-for="prize in prizeList"
          :key="prize.name"
          :prize="prize"
          :showButton="true"
        />
      </div>
      <div class="addPrize">
        <router-link to="/addprize">
          <button class="prize-button" v-show="isParent">Add Prize</button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import PrizeService from "../services/PrizeService";
import PrizeDetail from "../components/PrizeDetail.vue";

export default {
  name: "prize",
  components: {
    PrizeDetail,
  },
  data() {
    return {
      isLoading: true,
      prizeList: [],
      userGroup: "both",
    };
  },
  computed: {
    isParent: function () {
      return this.$store.state.user.authorities[0].name != "ROLE_CHILD";
    },
  },
  created() {
    PrizeService.listAllPrizes().then((response) => {
      console.log(response.data);
      this.prizeList = response.data;
      this.isLoading = false;
    });
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Libre+Baskerville&display=swap%27");
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");


.prizes {
  /* background: rgb(254, 209, 113); */
  background-color: rgba(254, 209, 113, 0.5);
  color: rgb(88, 85, 99);
  text-align: center;
  /* margin-left: 15%;
  margin-right: 15%; */
  padding: 15px;
  font-family: "Montserrat", sans-serif;
}

.details {
  margin-left: 12%;
  margin-right: 12%;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  background-color: rgb(254 209 113 / 56%);
  gap: 30px;
  padding: 30px;
  align-items: center;
}

button {
  margin-top: 35px;
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
}

</style>