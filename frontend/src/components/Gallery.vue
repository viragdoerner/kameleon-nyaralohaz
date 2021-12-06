<template>
  <div class="app-container">
    <div>
      <v-row>
        <v-col v-for="pic in pictures" :key="pic">
          <img :src="imgUrl" />
        </v-col>
      </v-row>
    </div>
    <div>
      <v-row>
        <v-col
          v-for="(pic, index) in pictures"
          :key="pic"
          class="d-flex child-flex"
          cols="4"
        >
          <v-img
            :src="$store.state.imgPath + pic"
            :lazy-src="`https://picsum.photos/10/6?image=${index * 5 + 10}`"
            aspect-ratio="1"
            class="grey lighten-2"
          >
            <template v-slot:placeholder>
              <v-row class="fill-height ma-0" align="center" justify="center">
                <v-progress-circular
                  indeterminate
                  color="grey lighten-5"
                ></v-progress-circular>
              </v-row>
            </template>
          </v-img>
        </v-col>
      </v-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "CGallery",
  props: ["pictures"],
  data() {
    return {
      imageList: [],
      imgPath: "../../public/images/apartments/",
      imgUrl: "../../public/images/apartments/FAKE.png",
      imgUrl2: "./../assets/img/FAKE.png",
      imgUrl3: '@/assets/img/parasol.png'
    };
  },
  mounted() {},
  computed: {
    getImgUrl() {
     /*  var url = this.imgPath + pic;
      console.log(url); */
      return "../../public/images/apartments/FAKE.png";
    },
  },
};
</script>

<style>
</style>
