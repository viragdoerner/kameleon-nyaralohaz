<template>
  <div class="app-container">
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
          <v-app-bar v-if="$store.getters.isLoggedIn"
            flat
            color="rgba(0, 0, 0, 0)"
          >
          <v-spacer></v-spacer>
            <v-btn
              color="white"
              icon
              @click="deletePicture(pic)"
            >
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
export default {
  name: "CGallery",
  props: ["pictures"],
  data() {
    return {
    };
  },
  mounted() {},
  methods: {
    deletePicture(pic){
      console.log(pic);
      this.$emit("delete-picture", pic);
    }
  },
};
</script>

<style>
</style>
