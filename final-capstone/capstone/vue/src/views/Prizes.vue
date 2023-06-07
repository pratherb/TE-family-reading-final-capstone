<template>
  <div class="prizes">
    <h2>Prize Details</h2>
    <div class="addPrize">
      <router-link to="/addprize">
        <button v-show="isParent">Add Prize</button>
      </router-link>
    </div>
      <prize-detail  v-for="prize in prizeList"
        :key="prize.name"
        :prize="prize"
        :showButton="true"/>
  </div>
</template>

<script>
import PrizeService from '../services/PrizeService';
import PrizeDetail from '../components/PrizeDetail.vue';

export default {
  name: 'prize',
  components: {
    PrizeDetail
  },
  data() {
    return {
      isLoading: true,
      prizeList: [],
      userGroup: 'both'
    }
  },
  computed: {
    isParent: function() {
      return this.$store.state.user.authorities[0].name != "ROLE_CHILD";
    } 
  },
  created() {
    PrizeService
      .listAllPrizes()
      .then((response) => {
        console.log(response.data)
        this.prizeList = response.data;
        this.isLoading = false;
      })
  }
}
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Libre+Baskerville&display=swap%27");

.prizes {
    background: rgb(254, 209, 113);
    color: rgb(88, 85, 99);
    font-family: 'Libre Baskerville';
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