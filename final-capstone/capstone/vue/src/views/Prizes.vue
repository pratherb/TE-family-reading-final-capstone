<template>
  <div class="prize">
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
      groupType: 'both'
    }
  },
  created() {
    PrizeService
      .listPrizes(this.groupType)
      .then((response) => {
        console.log(response.data)
        this.prizeList = response.data;
        this.isLoading = false;
      })
  }
}
</script>

<style>
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