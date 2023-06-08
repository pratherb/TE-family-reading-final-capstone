<template>
  <div>
    <div id="addPrize" class="text-center">
       <h1 class="prize-header">🌟 Add Prize 🌟</h1>
        <div class="behind-prize-form-text">
      <form class="prize-form" v-on:submit.prevent="addPrize">
        <div class="form-input-group-prize">
          <label  for="prize-name">Prize Name</label>
          <input type="text" id="prize-name" v-model="prize.name" required />
        </div>
        <div class="form-input-group-prize">
          <label class="prize-label" for="prize-description">Prize Description</label>
          <input
            type="text"
            id="prize-description"
            v-model="prize.description"
            required
          />
        </div>
        <div class="form-input-group-prize">
          <label class="prize-label" for="milestone">Milestone</label>
          <input
            type="text"
            id="milestone"
            v-model="prize.milestone"
            required
          />
        </div>
        <div class="form-input-group-prize">
          <label class="prize-label" for="user-group">User Group</label>
          <label class="prize-label" for="user-group">Parent</label>
          <input
            type="radio"
            name="family-group"
            value="parent"
            v-model="prize.userGroup"
            required
          />
          <label class="prize-label" for="user-group">Child</label>
          <input
            type="radio"
            name="family-group"
            value="child"
            v-model="prize.userGroup"
            required
          />
          <label class="prize-label" for="user-group">Both</label>
          <input
            type="radio"
            name="family-group"
            value="both"
            v-model="prize.userGroup"
            required
          />
        </div>
        <div class="form-input-group-prize">
          <label class="prize-label" for="max-prizes">Max Prizes</label>
          <input
            type="text"
            id="max-prizes"
            v-model="prize.maxPrizes"
            required
          />
        </div>
        <div class="form-input-group-prize">
          <label class="prize-label" for="start-date">Start Date</label>
          <input
            type="date"
            id="start-date"
            v-model="prize.startDate"
            required
          />
        </div>
        <div class="form-input-group-prize">
          <label class="prize-label" for="end-date">End Date</label>
          <input type="date" id="end-date" v-model="prize.endDate" required />
        </div>
        <button class="prize-button">Add Prize</button>
      </form>
      </div>
    </div>
  </div>
</template>

<script>
import prizeService from "../services/PrizeService";

export default {
  name: "addprize",
  data() {
    return {
      prize: {
        name: "",
        description: "",
        milestone: "",
        userGroup: "",
        maxPrizes: "",
        startDate: "",
        endDate: "",
      },
    };
  },
  methods: {
    addPrize() {
      const newPrize = {
        name: this.prize.name,
        description: this.prize.description,
        milestone: this.prize.milestone,
        userGroup: this.prize.userGroup,
        maxPrizes: this.prize.maxPrizes,
        startDate: this.prize.startDate,
        endDate: this.prize.endDate,
      };
      if (this.username != "") {
        prizeService.addToPrizeList(newPrize).then((response) => {
          if (response.status === 200) {
            this.prizes = {
              name: "",
              description: "",
              milestone: "",
              userGroup: "",
              maxPrizes: "",
              startDate: "",
              endDate: "",
            };
            this.$router.push({ path: "/prize" });
          }
        });
      }
    },
  },
};
</script>
<style scoped>
.prize-button {
  transition-duration: 0.4s;
  width:200px;
  height:40px;
  border: 1px solid rgb(0, 0, 0);
  margin-bottom:60px;
  background-color: rgb(231, 247, 217);
  font-family: "Montserrat", sans-serif;
  color: rgb(48, 46, 49);
  font-weight: bold;
  font-size:15px ;
}

.prize-button:hover {
  background-color: rgb(115, 147, 126);
}
</style>