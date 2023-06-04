<template>
  <div>
    <div id="add" class="text-center">
      <form v-on:submit.prevent="addForm">
        <h1>Add Prize</h1>
        <div class="form-input-group">
          <label for="prize-name">Prize Name</label>
          <input type="text" id="prize-name" v-model="prizes.prizeName" required />
        </div>
        <div class="form-input-group">
          <label for="prize-description">Prize Description</label>
          <input type="text" id="prize-desription" v-model="prizes.description" required/>
        </div>
        <div class="form-input-group">
          <label for="milestone">Milestone</label>
          <input type="text" id="milestone" v-model="prizes.milestone" required />
        </div>
        <div class="form-input-group">
          <label for="user-group">User Group</label>
          <input type="checkbox" id="user-group" v-model="prizes.userGroup" required />
        </div>
        <div class="form-input-group">
          <label for="max-prizes">Max Prizes</label>
          <input type="text" id="max-prizes" v-model="prizes.maxPrizes" required />
        </div>
        <div class="form-input-group">
          <label for="start-date">Start Date</label>
          <input type="date" id="start-date" v-model="prizes.startDate" required />
        </div>
        <div class="form-input-group">
          <label for="end-date">End Date</label>
          <input type="date" id="end-date" v-model="prizes.endDate" required />
        </div>
        <button type="submit">Create Prize</button>
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
        prizeName: "",
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
        prizeName: this.prizes.prizeName,
        description: this.prizes.description,
        milestone: this.prizes.milestone,
        userGroup: this.prizes.userGroup,
        maxPrizes: this.prizes.maxPrizes,
        startDate: this.prizes.startDate,
        endDate: this.prizes.endDate
      };
      if (this.username != "") {
        prizeService.add(newPrize).then((response) => {
          if (response.status === 200) {
            this.prizes = {
              prizeName: "",
              description: "",
              milestone: "",
              userGroup: "",
              maxPrizes: "",
              startDate: "",
              endDate: ""
            };
            this.$router.push({path: "/prizes"});
          }
        });
      }
    },
  },
};
</script>

<style>
</style>