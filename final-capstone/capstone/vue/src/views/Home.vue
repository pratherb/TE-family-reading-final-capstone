<template>
  <div class="home">
    <logo-slider>
  
  <div class="slideshow">
    <img src="../images/slideshowtest/_methode_times_prod_web_bin_c47f6830-9292-11ed-b04f-b9bf191ef388.jpg">
    <img src="../images/slideshowtest/download.jpg">
    <img src="../images/slideshowtest/english-bulldog.jpg">
    <img src="../images/slideshowtest/Full_attention_(8067543690).jpg">
    <img src="../images/slideshowtest/German-Pinscher-standing-near-a-lake.jpg">
    <img src="../images/slideshowtest/gettyimages-114166947-1-268128f97e5c415baede328c1fe32f55.jpg">
    <img src="../images/slideshowtest/samoyed-mother-dog-with-puppy-outdoors.jpg">
    
   </div>
  
  <div class="slideshow">
     <img src="../images/slideshowtest/_methode_times_prod_web_bin_c47f6830-9292-11ed-b04f-b9bf191ef388.jpg">
    <img src="../images/slideshowtest/download.jpg">
    <img src="../images/slideshowtest/english-bulldog.jpg">
    <img src="../images/slideshowtest/Full_attention_(8067543690).jpg">
    <img src="../images/slideshowtest/German-Pinscher-standing-near-a-lake.jpg">
    <img src="../images/slideshowtest/gettyimages-114166947-1-268128f97e5c415baede328c1fe32f55.jpg">
    <img src="../images/slideshowtest/samoyed-mother-dog-with-puppy-outdoors.jpg">
  </div>
   
</logo-slider>
    <div class="product_details1">
<h3>Introducing our innovative family reading tracking website, a must-have tool for modern families passionate about cultivating a love for reading. 
  With our user-friendly platform, you can effortlessly monitor and manage your family's reading habits, ensuring that everyone stays engaged and motivated to explore the world of literature.</h3>
      </div>
    <div class="book_covers1">
       <img src="../images/book covers/winniethepooh.jpg" alt="winnie the pooh">
      <img src="../images/book covers/aliceinwonderland.jpg" alt="alice in wonderland">
      <img src="../images/book covers/dracula.jpg" alt="dracula">
      <img src="../images/book covers/anneofgreengables.jpg" alt="anne of green gables">
      </div>
<div class="product_details2">
  <h3> Gone are the days of scattered reading logs and forgotten book lists. Our family reading tracking website offers a centralized hub where every family member can create their personal profiles, add books to their reading lists, and track their progress. 
    This interactive and personalized experience makes reading a shared journey, fostering a sense of camaraderie and healthy competition among family members.</h3>
</div>
<div class="book_covers2">
      <img src="../images/book covers/frankenstein.jpg" alt="frankie">
      <img src="../images/book covers/greatgatbsy.jpg" alt="great gatbsy">
      <img src="../images/book covers/GUEST_a0eb4016-52b3-4bbe-a1b9-8c208c81bb42.jpg" alt="wizard of oz">
      <img src="../images/book covers/gulliverstravels.jpg" alt="gulliver's traavels">
</div>
<div class="product_details3">
  <h3>With our website's comprehensive database of books, finding new reading material for every age group and interest becomes a breeze. Discover hidden gems, timeless classics, and trending titles curated by fellow bookworms within our vibrant community. 
    Whether it's picture books for the little ones or gripping novels for the adults, our platform caters to all tastes and preferences.To add an element of excitement, our family reading tracking website also offers gamification features. 
    Earn badges and rewards as you reach reading milestones, participate in reading challenges, and engage in discussions with other members. These gamified elements inject fun and motivation into the reading journey, making it an enjoyable and enriching experience for the entire family.
    Join us today and embark on an unforgettable literary adventure!</h3>
</div>
<div class="book_covers3">
      <img src="../images/book covers/jekyllhyde.jpg" alt="">
      <img src="../images\book covers/journeytocenterofearth.jpg" alt="">
      <img src="../images\book covers/littlewomen.jpg" alt="">
      <img src="../images\book covers/peterrabbit.jpg" alt="">
</div>
<div class="outro">
  <h1>this is the outro section with link to register page and goodbye message</h1>
</div>
 <div class="book_covers4">
   <img src="../images/book covers/pictureofdoriangray.jpg" alt="dorian gray">
   <img src="../images/book covers/theOdyssey.jpg" alt="odyessy">
   <img src="../images/book covers/velvateenrabbit.jpg" alt="velvateen rabbit">
   <img src="../images/book covers/wutheringheights.jpg" alt="wuthering heights">
 </div>
  </div>
</template>

<script>
export default {
  name: "home"
};
</script>

<style scoped>

.book_covers1{
  display: flex;
  flex-direction: row;
}
.book_covers2{
  display: flex;
  flex-direction: row;
}
.book_covers3{
  display: flex;
  flex-direction: row;
}
.book_covers4{
  display: flex;
  flex-direction: row;
}
logo-slider {
 background: rgb(115,147,126);
  border: 2px solid rgb(0, 0, 0);
  padding: 20px;
  overflow: hidden;
  max-width: 1000px;
  width: 100%;
  margin: 0 auto;	
	position: relative;
	display: flex;
  
}
	logo-slider:hover .slideshow{
		animation-play-state: paused;
	}

  .slideshow { 
		display: flex;
		position: relative;
		animation: marquee 15s linear infinite;
    justify-content: space-around;
    
	}
		
	img {
		display: block;
		min-width: 50px;
		height: 200px;
    width:200px;
		margin: 0 1vw;
	}
  
  after {
		content: "";
		position: absolute;
		width: 100%;
		height: 100%;
		left: 0;
		top: 0;
		background: linear-gradient(to right, white, rgba(white, 0) 80px, rgba(white, 0) calc(100% - 80px), white);
	}

@keyframes marquee {
	0%   { transform: translateX(0%); }
	100% { transform: translateX(-100%); }
}

</style>