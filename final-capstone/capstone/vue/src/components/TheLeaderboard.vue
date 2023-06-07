<template>
  <div>
    <div class="loading" v-if="isLoading">
      <img src="../assets/book_pages_opening.gif" />
    </div>
      <ul v-else>
          <h3 class="leaderboard-head">🌟 Leaderboard 🌟</h3>
          <br>
          <li class="score" v-for="score in scores" v-bind:key="score.id">
              <p>{{score}}</p>
          </li>
      </ul>

</div>
</template>

<script>
import activityService from '../services/ActivityService'

export default {
    name: "leaderboard",
    data () {
        return {
            scores: [],
            isLoading: true
        };
    },
    created() {
        activityService
            .getLeaderboard(this.$store.state.user.familyId)
            .then((response)=> {
                this.scores = response.data;
                this.isLoading = false;
            })
    }
}
</script>

<style>

</style>