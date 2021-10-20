<template>
  <v-app>
    <c-app-bar></c-app-bar>
    <v-main>
      <router-view />
    </v-main>
    <c-footer></c-footer>
  </v-app>
</template>

<script>
import CFooter from "./components/Footer.vue";
import CAppBar from "./components/AppBar.vue";

export default {
  components: {
    CFooter,
    CAppBar,
  },
  name: "App",

  data: () => ({
    //
  }),
};
</script>


<style>

@font-face {
  font-family: "Zabatana";
  src: local("Zabatana"),
   url(./assets/fonts/Zabatana-Poster.ttf) format("truetype");
}

</style>