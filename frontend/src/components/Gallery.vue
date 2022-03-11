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
          v-for="(pic, thumbIndex) in pictures"
          :key="thumbIndex"
          class="d-flex child-flex"
          cols="4"
        >
          <v-img
            :src="$store.state.imgPath + pic"
            :lazy-src="`https://picsum.photos/10/6?image=${index * 5 + 10}`"
            aspect-ratio="1"
            class="grey lighten-2" id="picture"
            @click="index = thumbIndex"
          >
            <v-app-bar
              v-if="$store.getters.loggedIn && $store.getters.isAdmin"
              flat
              color="rgba(0, 0, 0, 0)"
            >
              <v-spacer></v-spacer>
              <v-btn color="white" icon @click="openDialog($event, pic)">
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
    <v-dialog v-model="dialog" max-width="290">
      <v-card>
        <v-card-title> Biztos törölni szeretnéd? </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="cgreen darken-1" text @click="dialog = false">
            Mégse
          </v-btn>
          <v-btn color="corange darken-1" text @click="deletePicture()">
            Törlés
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
      index: null,
      dialog: false,
      pictureToBeRemoved: null,
    };
  },
  mounted() {},
  methods: {
    deletePicture() {
      this.$emit("delete-picture", this.pictureToBeRemoved);
       this.dialog = false;
    },
    openDialog(e, pic) {
      e.stopImmediatePropagation();
      this.dialog = true;
      this.pictureToBeRemoved = pic;
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
#picture{
  cursor: pointer;
}
</style>
