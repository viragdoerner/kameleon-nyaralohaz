<template>
  <div>
    <v-app-bar color="cgreen" dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-toolbar-title>Kaméleon nyaralóház</v-toolbar-title>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" absolute temporary>
      <v-list nav dense>
        <v-list-item-group
          v-model="group"
          active-class="corange--text text--accent-4"
        >
          <v-list-item to="/">
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Kezdőlap</v-list-item-title>
          </v-list-item>

          <v-list-item
            v-for="route in apartment_routes"
            v-bind:key="route.id"
            :to="route.url"
          >
            <v-list-item-icon>
              <v-icon>mdi-home-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>{{ route.name }}</v-list-item-title>
          </v-list-item>

          <v-list-item to="/reserve">
            <v-list-item-icon>
              <v-icon>mdi-calendar</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Foglalás</v-list-item-title>
          </v-list-item>
          <v-list-item v-if="!$store.getters.loggedIn" to="/login">
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Bejelentkezés</v-list-item-title>
          </v-list-item>
          <v-list-item v-if="$store.getters.loggedIn && $store.getters.isAdmin" to="/user-management">
            <v-list-item-icon>
              <v-icon>mdi-account-multiple</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Felhasználókezelés</v-list-item-title>
          </v-list-item>
        <v-list-item v-if="$store.getters.loggedIn" @click="logout">
            <v-list-item-icon>
              <v-icon>mdi-account-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Kijelentkezés</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: "CAppBar",
  data: () => ({
    drawer: false,
    group: null,
  }),
  props: ["apartment_routes"],
  methods: {
    logout: function () {
      this.$store.dispatch("auth/logout").then(() => {
        this.$router.push("/login");
      });
      this.drawer = !this.drawer;
    },
  },
};
</script>