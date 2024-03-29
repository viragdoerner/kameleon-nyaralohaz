<template>
  <v-app>
    <c-app-bar
      v-if="$route.name !== 'Login'"
      :apartment_routes="apartment_routes"
    ></c-app-bar>
    <v-main class="app">
      <router-view :key="$route.path" />
    </v-main>
    <c-footer v-if="$route.name !== 'Login'"></c-footer>
    <confirm-dialog></confirm-dialog>
    <c-snack-bar></c-snack-bar>
  </v-app>
</template>

<script>
import CFooter from "./components/Footer.vue";
import CAppBar from "./components/AppBar.vue";
import CSnackBar from "./components/SnackBar.vue";
import CApartment from "./views/Apartment.vue";
import router from "./router";
import ApiService from "./services/api.service";
import ConfirmDialog from "./components/ConfirmDialog.vue";

export default {
  components: {
    CFooter,
    CAppBar,
    CSnackBar,
    ConfirmDialog,
  },
  name: "App",

  data: () => ({
    apartment_routes: [],
  }),
  methods: {
    getDynamicRoutes(url) {
      ApiService.GET(url)
        .then((response) => {
          this.processData(response, this);
        })
        .catch((err) =>
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: err,
          })
        );
    },

    processData: ({ data }, that) => {
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
          name: `${apartment.name}`,
          props: { apartment: apartment },
        };
        router.addRoute(newRoute);
        that.apartment_routes.push({
          name: apartment.name,
          url: newRoute.path,
          id: apartment.id,
        });
      });
    },
  },
  created() {
    this.getDynamicRoutes("apartment", this);
  },
  mounted() {},
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
.v-data-table-header-mobile {
  display: none !important;
}
</style>
