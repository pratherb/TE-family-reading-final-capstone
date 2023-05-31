<template>
  <div class="members">
      <h2>BookBound Image Here</h2>
      <div id="nav">
          <router-link v-bind:to="{ name: 'reading' }"> Reading </router-link>
          <router-link v-bind:to="{ name: 'prizes' }"> Prizes </router-link>
          <router-link v-bind:to="{ name: 'settings' }"> Settings </router-link>
      </div>
      <h3 id="member-heading">Members</h3>
       <ul v-for="member in filteredMembers" v-bind:key="member.name">
          <li>
              {{ member.name }}
          </li>
      </ul>
  </div>
</template>

<script>
export default {
    data() {
        return {
            filterText: '',
            members: [
                {
                name: 'Admin',
                admin: true
                },
                {
                name: 'Bob',
                admin: false
                }
            ]
        }
    },
    computed: {
        filteredMembers() {
            return this.members.filter((member) => {
                return member.name.includes(this.filterText);
            })
        }
    }
}
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Libre+Baskerville&display=swap%27");

.members {
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

.nav {
    
}

</style>