<template>
  <div>
    <div id="add" class="text-center">
      <form v-on:submit.prevent="addPrize">
        <h1>Add Prize</h1>
        <div class="form-input-group">
          <label for="prize-name">Prize Name</label>
          <input type="text" id="prize-name" v-model="prize.name" required />
        </div>
        <div class="form-input-group">
          <label for="prize-description">Prize Description</label>
          <input type="text" id="prize-description" v-model="prize.description" required/>
        </div>
        <div class="form-input-group">
          <label for="milestone">Milestone</label>
          <input type="text" id="milestone" v-model="prize.milestone" required />
        </div>
        <div class="form-input-group">
          <label for="user-group">User Group</label>
          <label for="user-group" >Parent</label>
          <input type="radio" name="family-group" id="user-group" v-model="prize.userGroup" required>
          <label for="user-group" >Child</label>
          <input type="radio" name="family-group" id="user-group" v-model="prize.userGroup" required>
          <label for="user-group" >Both</label>
          <input type="radio" name="family-group" id="user-group" v-model="prize.userGroup" required>
        </div>
        <div class="form-input-group">
          <label for="max-prizes">Max Prizes</label>
          <input type="text" id="max-prizes" v-model="prize.maxPrizes" required />
        </div>
        <div class="form-input-group">
          <label for="start-date">Start Date</label>
          <input type="date" id="start-date" v-model="prize.startDate" required />
        </div>
        <div class="form-input-group">
          <label for="end-date">End Date</label>
          <input type="date" id="end-date" v-model="prize.endDate" required />
        </div>
        <button>Add to Prize List</button>
      </form>
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
        endDate: ""
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
        endDate: this.prize.endDate
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
              endDate: ""
            };
            this.$router.push({path: "/prize"});
          }
        });
      }
    },
  },
};
</script>

<style>

</style>