<template>
  <div>
      <add-prize-form/>
       <div class="run">
          <p>
            📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘
            📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗
            📘 📓📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒 📗 📘 📓 📕 📙 📒
            📗 📘 📓
          </p>
        </div>
  </div>
</template>

<script>
import addPrizeForm from "../components/AddPrizeForm";

export default {
    components: {
        addPrizeForm
    }
}
</script>
<style>
@import url("https://fonts.cdnfonts.com/css/socake");
@import url("https://fonts.googleapis.com/css2?family=Montserrat&display=swap");

#addPrize {
  background: rgb(115, 147, 126);
}
.prize-header {
  font-family: "Socake", sans-serif;
  background: rgb(244, 252, 172);
  border: solid 2px rgb(248, 226, 102);
  border-radius: 4px;
  margin: 0px auto 60px auto;
  /* top, right, bottom, left */
  text-align: center;
  padding-top: 30px;
  color: rgb(88,85,99);
  height:120px;
  font-size: 50px;
}
.prize-form {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  align-content: center;
  align-items: baseline;
}
.form-input-group-prize {
  display: flex;
  border: solid, 1px, black;
  margin-bottom: 1rem;
  color: rgb(88,85,99);
  font-weight: bold;
  font-size: 20px;
}
.prize-label {
  font-family: "Montserrat", sans-serif;
}
.behind-prize-form-text {
  background: rgba(232, 123, 31, 0.8);
  display: flex;
  flex-direction: column;
  padding-top: 30px;
  align-items: flex-start;
  width: 900px;
  height: 350px;
  justify-content: space-between;
  border-radius: 8px;
  border: solid 3px rgb(182, 90, 10);
 margin-left: 450px;
}
.run{
  display: flex;
  background: rgb(115, 147, 126);
  height: 90px;
  padding-top:260px;
}

label {
    font-family: "Montserrat", sans-serif;
}
.prize-button{
 transition-duration: 0.4s;
  border: 1px solid rgb(255, 191, 16);
  margin: 50px 30px 40px 30px;
  background-color: rgb(244, 252, 172);
  font-family: "Montserrat", sans-serif;
  color: rgb(88,85,99);
  font-weight: bold;
  font-size:15px ;
  margin-top:20px;
  width: 100px;
  height:40px;
}
.prize-button:hover{
  background-color: rgb(255, 191, 16);
}
</style>