<template>
  <div class="py-16 gallery-section">
    <v-container class=" col-10">
    <c-gallery></c-gallery>
  </v-container>
  </div>
</template>

<script>
import CGallery from "../Gallery.vue";
export default {
  name: "CGallerySection",
  components: {
    CGallery,
  },
  data: () => ({}),
  computed: {},
  mounted() {},
  methods: {},
};
</script>

<style  scoped>
.gallery-section {
  background-color: white;
}
</style>
