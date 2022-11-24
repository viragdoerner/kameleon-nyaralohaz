<template>
  <div>
    <v-app-bar color="cgreen" dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-toolbar-title>Kaméleon nyaralóház</v-toolbar-title>
      <v-spacer></v-spacer>

      <v-btn depressed to="/booking" color="corange"> Foglalás </v-btn>
      <v-btn depressed to="/login" color="clightgreen" v-if="!loggedIn" class="ml-3"> Bejelentkezés </v-btn>

      <v-menu bottom left v-if="loggedIn">
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on" class="mx-3">
            <v-icon large>mdi-account</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item>
            <v-btn text to="/user-booking"> Foglalásaim</v-btn>
          </v-list-item>
          <v-list-item v-if="loggedIn" >
            <v-btn text @click="logout"> Kijelentkezés </v-btn>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" app temporary>
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

           <v-list-item to="/user-booking" v-if="loggedIn">
            <v-list-item-icon>
              <v-icon>mdi-list-box-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Foglalásaim</v-list-item-title>
          </v-list-item>

          
          
          
          <v-list-item v-if="isAdmin" disabled class="mt-5">
            <p class="text-caption corange--text">Admin funkciók</p>
          </v-list-item>
          <v-list-item v-if="isAdmin" to="/user-management">
            <v-list-item-icon>
              <v-icon>mdi-account-multiple</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Felhasználókezelés</v-list-item-title>
          </v-list-item>
          <v-list-item v-if="isAdmin" to="/admin-booking">
            <v-list-item-icon>
              <v-icon>mdi-list-box</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Foglalások kezelése</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
      <template v-slot:append>
        <div class="pa-2 mb-10">
           <v-btn block color="corange" dark to="booking" class="mb-3">
            Foglalás
           <v-icon right dark> mdi-calendar </v-icon>
          </v-btn>
          <v-btn block color="cgreen" dark v-if="loggedIn" @click="logout">
            Kijelentkezés
            <v-icon right dark> mdi-logout </v-icon>
          </v-btn>
          <v-btn block color="clightgreen" dark v-if="!loggedIn" to="/login">
            Bejelentkezés
            <v-icon right dark> mdi-login </v-icon>
          </v-btn>
        </div>
      </template>
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
  computed: {
    isAdmin() {
      return this.$store.getters.loggedIn && this.$store.getters.isAdmin;
    },
    loggedIn() {
      return this.$store.getters.loggedIn;
    },
  },
};
</script>