<template>
  <div class="app-container">
    <div>
      <v-row>
        <LightGallery
          :images="pictureURLs"
          :index="index"
          :disable-scroll="true"
          @close="index = null"
        />
        <v-col
          v-for="(pic, thumbIndex) in pictureURLs"
          :key="thumbIndex"
          @click="index = thumbIndex"
          class="d-flex child-flex"
          cols="4"
        >
          <v-img
            :src="pic"
            :lazy-src="`https://picsum.photos/10/6?image=${index * 5 + 10}`"
            aspect-ratio="1"
            class="grey lighten-2"
          >
            <v-app-bar
              v-if="$store.getters.isLoggedIn"
              flat
              color="rgba(0, 0, 0, 0)"
            >
              <v-spacer></v-spacer>
              <v-btn color="white" icon @click="deletePicture(pic)">
                <v-icon>fa-close</v-icon>
              </v-btn>
            </v-app-bar>
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

    <!-- <ul>
      <li
        v-for="(pic, thumbIndex) in pictureURLs"
        :key="thumbIndex"
        @click="index = thumbIndex"
      >
        <img :src="pic" />
      </li>
    </ul> -->
  </div>
</template>

<script>
import { LightGallery } from "vue-light-gallery";
export default {
  name: "CGallery",
  props: ["pictures"],
  components: {
    LightGallery,
  },
  data() {
    return {
      images: [
        { title: "img 1", url: "path/to/image_1.jpg" },
        { title: "img 2", url: "path/to/image_2.jpg" },
      ],
      index: null,
    };
  },
  mounted() {},
  methods: {
    deletePicture(pic) {
      console.log(pic);
      this.$emit("delete-picture", pic);
    },
  },
  computed: {
    pictureURLs() {
      var picURLs = [];
      this.pictures.forEach((pic) => {
        picURLs.push(this.$store.state.imgPath + pic);
      });
      return picURLs;
    },
  },
};
</script>

<style>
</style>
