<template>
  <!--    Made by Erik Terwan    -->
  <div id="app">
    <nav role="navigation">
      <div id="menuToggle">
        <input type="checkbox" />

        <span></span>
        <span></span>
        <span></span>

        <ul id="menu">
          <router-link v-bind:to="{ name: 'home' }" @click="closeMenu">Home</router-link
          ><br />
          <router-link v-bind:to="{ name: 'members' }" @click="closeMenu">Members</router-link
          ><br />
          <router-link v-bind:to="{ name: 'login' }" v-if="!$store.state.token">Login</router-link>
          <router-link
            v-bind:to="{ name: 'logout' }"
            v-if="$store.state.token != ''"
            @click="closeMenu">Logout</router-link
          ><br />
          <router-link v-bind:to="{ name: 'aboutus' }" @click="closeMenu">About Us</router-link>
        </ul>
      </div>
    </nav>
    <router-view />
  </div>
  <!-- Nav bar from VitorLuizC -->
</template>

<script>
export default {
  methods: {
    closeMenu() {
      const checkbox = document.querySelector("#menuToggle input");
      checkbox.checked = false;
    },
  },
  watch: {
    $route() {
      this.closeMenu();
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");
body {
  margin: 0;
  padding: 0;

  background: #232323;
  color: #cdcdcd;
  font-family: "Montserrat", sans-serif;
}

#menuToggle {
  display: block;
  position: fixed;
  top: 50px;
  left: 50px;

  z-index: 1;

  -webkit-user-select: none;
  user-select: none;
}

#menuToggle a {
  text-decoration: none;
  color: #232323;
  font-size: 25px;
  transition: color 0.3s ease;
}

#menuToggle a:hover {
  color: tomato;
}

#menuToggle input {
  display: block;
  width: 40px;
  height: 32px;
  position: absolute;
  top: -7px;
  left: -5px;

  cursor: pointer;

  opacity: 0;
  z-index: 2;

  -webkit-touch-callout: none;
}

#menuToggle span {
  display: block;
  width: 33px;
  height: 4px;
  margin-bottom: 5px;
  position: relative;

  background: rgb(88, 85, 91);
  border-radius: 3px;

  z-index: 1;

  transform-origin: 4px 0px;

  transition: transform 0.5s cubic-bezier(0.77, 0.2, 0.05, 1),
    background 0.5s cubic-bezier(0.77, 0.2, 0.05, 1), opacity 0.55s ease;
}

#menuToggle span:first-child {
  transform-origin: 0% 0%;
}

#menuToggle span:nth-last-child(2) {
  transform-origin: 0% 100%;
}

#menuToggle input:checked ~ span {
  opacity: 1;
  transform: rotate(45deg) translate(-2px, -1px);
  background: #232323;
}

#menuToggle input:checked ~ span:nth-last-child(3) {
  opacity: 0;
  transform: rotate(0deg) scale(0.2, 0.2);
}

#menuToggle input:checked ~ span:nth-last-child(2) {
  transform: rotate(-45deg) translate(0, -1px);
}

#menu {
  position: absolute;
  width: 100px;
  margin: -100px 0 0 -50px;
  padding: 50px;
  padding-top: 125px;

  /* background: #ededed; */
  list-style-type: none;
  -webkit-font-smoothing: antialiased;

  transform-origin: 0% 0%;
  transform: translate(-100%, 0);

  transition: transform 0.5s cubic-bezier(0.77, 0.2, 0.05, 1);
}

#menu li {
  padding: 10px 0;
  font-size: 22px;
}

#menuToggle input:checked ~ ul {
  transform: none;
}
</style>