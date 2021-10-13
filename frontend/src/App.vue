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
import CFooter from "./views/Footer.vue";
import CAppBar from "./views/AppBar.vue";

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
