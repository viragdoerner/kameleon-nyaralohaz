<template>
  <div>
    <c-file-upload
      v-if="$store.state.admin"
      :apartmentId="apartment.id"
      v-on:uploaded-pictures="onUploadedPictures"
    ></c-file-upload>
    <div class="py-16 gallery-section">
      <v-container class="col-10">
        <c-gallery></c-gallery>
      </v-container>
    </div>
  </div>
</template>

<script>
import CFileUpload from "./FileUpload.vue";
import CGallery from "../Gallery.vue";
export default {
  name: "CGallerySection",
  components: {
    CFileUpload,
  },
  props: ["apartment"],
  data: function () {
    return {};
  },
  components: {
    CGallery,
  },
  data: () => ({}),
  computed: {},
  mounted() {},
  methods: {
    onUploadedPictures(updatedApartment) {
      this.apartment = updatedApartment;
    },
  },
};
</script>

<style  scoped>
.gallery-section {
  background-color: white;
}
</style>
