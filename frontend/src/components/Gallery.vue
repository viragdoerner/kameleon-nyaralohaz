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
          class="d-flex child-flex col-md-4 col-6"
        >
          <v-img
            :src="pic"
            :lazy-src="`https://picsum.photos/10/6?image=${index * 5 + 10}`"
            aspect-ratio="1"
            class="grey lighten-2"
            id="picture"
            @click="index = thumbIndex"
          >
            <v-app-bar
              v-if="$store.getters.loggedIn && $store.getters.isAdmin"
              flat
              color="rgba(0, 0, 0, 0)"
            >
              <v-spacer></v-spacer>
              <v-btn color="white" icon @click.stop="openDialog( pic)">
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
      pictureToBeRemoved: null,
    };
  },
  mounted() {},
  methods: {
    deletePicture() {
      this.$emit("delete-picture", this.pictureToBeRemoved);
    },
    openDialog(pic) {
      this.$store.commit("dialog/openSimpleDialog", {
        title: "Biztosan törölni szeretnéd?",
        confirmButton: "Törlés",
        onConfirm: () => {
          return this.deletePicture();
        },
      });
      this.pictureToBeRemoved = pic;
    },
  },
  computed: {
    pictureURLs() {
      var picURLs = [];
      this.pictures.forEach((pic) => {
        picURLs.push(pic);
      });
      return picURLs;
    },
  },
};
</script>

<style>
#picture {
  cursor: pointer;
}
</style>
