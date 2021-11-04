<template>
  <div>
    <v-app-bar color="cgreen" dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-toolbar-title>Title</v-toolbar-title>
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

          <v-list-item to="/foldszinti-apartman">
            <v-list-item-icon>
              <v-icon>mdi-home-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Földszinti apartman</v-list-item-title>
          </v-list-item>

          <v-list-item to="/emeleti-apartman">
            <v-list-item-icon>
              <v-icon>mdi-home-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Emeleti apartman</v-list-item-title>
          </v-list-item>

          <v-list-item to="/reserve">
            <v-list-item-icon>
              <v-icon>mdi-calendar</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Foglalás</v-list-item-title>
          </v-list-item>
           <v-list-item to="/login">
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Bejelentkezés</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: "CAppBar",

  components: {},
  data: () => ({
    drawer: false,
    group: null,
  }),
};
</script>