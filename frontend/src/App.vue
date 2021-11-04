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
import CApartment from "./views/Apartment.vue";
import axios from "axios";
import router from "./router";

export default {
  components: {
    CFooter,
    CAppBar,
  },
  name: "App",

  data: () => ({
    //
  }),
  methods: {
    getDynamicRoutes(url) {
      axios
        .get(url)
        .then(this.processData)
        .catch((err) => console.log(err));
    },

    processData: ({ data }) => {
      console.log(data);
      data.forEach((apartment) => {
        console.log(apartment.id);
        let newRoute = {
          path: `/${apartment.id}`,
          component: CApartment,
          name: `${apartment.id}_index`,
          props: { entity_type_id: apartment.id },
        };
        router.addRoute(newRoute);
      });
    },
  },

  created() {
    this.getDynamicRoutes(this.$store.state.baseURL + "apartment");
  },
};
</script>


<style>
@font-face {
  font-family: "Zabatana";
  src: local("Zabatana"),
    url(./assets/fonts/Zabatana-Poster.ttf) format("truetype");
}
</style>