<template>
  <div class="aboutus">
    <div class="head">
      <h1>About Us</h1>
      <img class="title-logo" src="../images/sharpened-transparent-logo.png" />
    </div>
    <div class="flexbox">
      <div>
        <img src="../images/Ava.jpg" id="ava" alt="" />
        <br />
        <p class="name">Ava Lindstrom</p>
        <a href="https://www.linkedin.com/in/ava-lindstrom/" id="ava-li"
          >LinkedIn</a
        >
        <br />
        <p class="about">Coding enthusiast, crossword puzzler, and comic book fanatic. 
          Vegetarian bruncher, omnivorous reader. Likely on a train right now.</p>
        <br />
        <p class="location">Location: Chicago, IL</p>
      </div>
      <br />
      <div>
        <img src="../images/Nova.jpg" id="nova" alt="" />
        <br />
        <p class="name">Nova Widdifield</p>
        <a href="https://www.linkedin.com/in/nova-widdifield/" id="nova-li"
          >LinkedIn</a
        >
        <br />
        <p class="about">Nova's text here</p>
        <br />
        <p class="location">Location: Aurora, CO</p>
      </div>
      <br />
      <div>
        <img src="../images/Vince.jpg" id="vince" alt="" />
        <br />
        <p class="name">Vince Patterson</p>
        <a href="https://www.linkedin.com/in/vincent-patterson/" id="vince-li"
          >LinkedIn</a
        >
        <br />
        <p class="about">
          Cat father of 2 rapscallions. Web devloper, flavored water drinker,
          statically-typed language enjoyer, and part-time aspiring guitarist.
        </p>
        <br />
        <p class="location">Location: Springfield, OH</p>
      </div>
      <br />
      <div>
        <img src="../images/Blake.jpg" id="blake" alt="" />
        <br />
        <p class="name">Blake Prather</p>
        <a href="https://www.linkedin.com/in/blake-c-prather/" id="blake-li"
          >LinkedIn</a
        >
        <br />
        <p class="about">
          Father to three awesome little boys. Husband to Cera. Career changer
          from State Trooper to software developer. Outdoorsy. Novice gardener.
        </p>
        <br />
        <p class="location">Location: Delaware, OH</p>
      </div>
      <br />
    </div>
  </div>
</template>

<script>
export default {
  name: "aboutus",
};
</script>

<style scoped>
@import url("https://fonts.cdnfonts.com/css/socake");
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@200&display=swap");

.aboutus {
  padding: 25px;
  background: rgba(254, 209, 113, 0.5);
  align-items: center;
}

.flexbox {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
}

h1 {
  display: flex;
  font-family: "Socake", sans-serif;
  color: rgb(88, 85, 91);
  font-size: 80px;
}

a {
  display: flex;
  font-family: "Montserrat", sans-serif;
  font-size: 25px;
  justify-content: center;
}

img {
  justify-content: center;
  align-items: center;
  border-radius: 1000px;
  margin: 20px;
  margin-left: 40px;
}

.name {
  display: flex;
  font-family: "Socake", sans-serif;
  font-size: 36px;
  justify-content: center;
  margin: 0 auto;
}

.about {
  display: flex;
  font-family: "Montserrat", sans-serif;
  font-weight: bold;
  text-align: center;
  justify-content: center;
  align-items: center;
  background: rgb(115, 147, 126);
  border: 2px;
  border-color: rgb(115, 147, 126);
  border-style: solid;
  border-radius: 80px;
  padding: 23px;
  margin: 0 auto;
  width: 390px;
}

.location {
  display: flex;
  font-family: "Montserrat", sans-serif;
  font-style: oblique;
  text-align: center;
  justify-content: center;
  align-items: center;
  background: rgb(115, 147, 126);
  border: 2px;
  border-color: rgb(115, 147, 126);
  border-style: solid;
  border-radius: 80px;
  padding: 23px;
  margin: 0 auto;
  width: 200px;
}

#ava {
  display: flex;
  width: 350px;
  border: black;
  border-width: 5px;
  border-style: solid;
}

#nova {
  display: flex;
  width: 350px;
  border: black;
  border-width: 5px;
  border-style: solid;
}

#vince {
  display: flex;
  width: 350px;
  border: black;
  border-width: 5px;
  border-style: solid;
}

#blake {
  display: flex;
  width: 350px;
  border: black;
  border-width: 5px;
  border-style: solid;
}

.head {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  height: 375px;
}
</style>