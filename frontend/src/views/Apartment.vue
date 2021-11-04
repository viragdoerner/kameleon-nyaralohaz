<template>
  <div>
    <c-header></c-header>
    apartment {{entity_type_id}}
  </div>
</template>

<script>
import CHeader from "../components/home/Header.vue";

import axios from "axios";

export default {
  name: "CApartment",
props: ["entity_type_id"],
  components: {},
  components: {
    CHeader
  },
  data: () => ({
    apartment: {},
  }),
  mounted() {
    
  },
  methods: {
    getApartments() {
      
    }
  },
};
</script>
