<template>
  <div>
    <div class="loading" v-if="isLoading">
      <img src="../assets/book_pages_opening.gif" />
    </div>
    <div v-else>
      <h4>Total Books Read:</h4>
      <h4>{{bookTotal}}</h4>
      <h4>Total Minutes of Reading:</h4>
      <h4>{{minuteTotal}}</h4>
    </div>
  </div>
</template>

<script>
import readingListService from '../services/ReadingListService';
import activityService from '../services/ActivityService';
export default {
    name:"totals",
    isLoading: true,
    data(){
        return {
            bookTotal: 0,
            minuteTotal: 0
        }
    },
    created() {
        readingListService
            .getNumBooksFinishedPerUser(this.$route.params.username)
            .then((response)=> {
                this.bookTotal = response.data;
            })
        activityService
            .getTotalMinutesReadPerUser(this.$route.params.username)
            .then((response)=> {
                this.minuteTotal = response.data;
                this.isLoading = false;
            })
    }

}
</script>

<style>

</style>