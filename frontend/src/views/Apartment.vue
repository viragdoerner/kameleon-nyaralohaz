<template>
  <div>
    <c-header></c-header>
    apartment {{apartment.name}}
  </div>
</template>

<script>
import CHeader from "../components/home/Header.vue";

import axios from "axios";

export default {
  name: "CApartment",
props: ["apartment"],
  components: {
    CHeader
  },
  data: () => ({
  }),
  mounted() {
    
  },
  methods: {
    getApartments() {
      
    }
  },
};
</script>
