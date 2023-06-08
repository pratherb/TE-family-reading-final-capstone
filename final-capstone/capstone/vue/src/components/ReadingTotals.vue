<template>
  <div>
    <div class="loading" v-if="isLoading">
      <img src="../assets/book_pages_opening.gif" />
    </div>
    <div v-else class="totals">
      <h4 class="total-books-read">Total Books Read:</h4>
      <h4 class="book-total">{{bookTotal}}</h4>
      <h4 class="total-minutes-read">Total Minutes of Reading:</h4>
      <h4 class="minute-total">{{minuteTotal}}</h4>
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
<style scoped>
.totals h4{
  margin-top: 5px;
  margin-bottom: 5px;

}
</style>