<template> <!-- DISPLAYED UPON LOGOUT -- NEED A TIMEOUT TO GO BACK TO HOME -->
  <h1>Logout</h1>
</template>

<script>
export default {
  created() {
    this.$store.commit("LOGOUT");
    this.$router.push("/login");
    /* setTimeout(function () { this.fetchHole() }.bind(this), 1000) */ /* Possible timeout code? */
  }
};
</script>
