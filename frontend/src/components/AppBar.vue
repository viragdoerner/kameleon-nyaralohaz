<template>
  <div>
    <v-app-bar color="cgreen" dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-toolbar-title>Kaméleon nyaralóház</v-toolbar-title>
      <v-spacer></v-spacer>
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