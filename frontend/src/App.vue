<template>
  <v-app>
    <c-header></c-header>
    <v-main>
      <router-view/>
    </v-main>
    <c-footer></c-footer>
  </v-app>
</template>

<script>

import CFooter from './views/Footer.vue';
import CHeader from './views/Header.vue';

export default {
  components: { 
    CFooter,
    CHeader
  },
  name: 'App',

  data: () => ({
    //
  }),
};
</script>
