<template>
  <v-app>
    <c-app-bar></c-app-bar>
    <v-main class="app">
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
      var slugifyString = function (str) {
        str = str.replace(/^\s+|\s+$/g, ""); // trim
        str = str.toLowerCase();

        // remove accents, swap ñ for n, etc
        var from = "ãàáäâẽèéëêìíïîõòóöôùúüûñç·/_,:;";
        var to = "aaaaaeeeeeiiiiooooouuuunc------";
        for (var i = 0, l = from.length; i < l; i++) {
          str = str.replace(new RegExp(from.charAt(i), "g"), to.charAt(i));
        }

        str = str
          .replace(/[^a-z0-9 -]/g, "") // remove invalid chars
          .replace(/\s+/g, "-") // collapse whitespace and replace by -
          .replace(/-+/g, "-"); // collapse dashes

        return str;
      };
      data.forEach((apartment) => {
        let newRoute = {
          path: `/${slugifyString(apartment.name)}`,
          component: CApartment,
          name: `${apartment.id}_index`,
          props: { apartment: apartment },
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
.app {
  background-color: #d4ecdd;
}
.transparentCard {
  background-color: rgba(255, 255, 255, 0) !important;
  border-color: white !important;
}
</style>
