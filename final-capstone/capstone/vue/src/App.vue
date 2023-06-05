<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''"> Logout </router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'members' }">Members</router-link>&nbsp;|&nbsp;
        <router-link v-bind:to="{ name: 'login' }" v-if="!$store.state.token">Login </router-link>&nbsp;|&nbsp;
    </div>
    <router-view />
  </div>
</template>
