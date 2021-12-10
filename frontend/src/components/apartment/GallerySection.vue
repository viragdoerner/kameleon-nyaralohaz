<template>
    <div class="py-16 gallery-section">
      <c-file-upload
      v-if="$store.getters.isLoggedIn"
      :apartmentId="apartment.id"
      v-on:uploaded-pictures="onUploadedPictures"
    ></c-file-upload>
      <v-container class="col-10">
        <c-gallery  :pictures="apartment.pictures"></c-gallery>
      </v-container>
    </div>
</template>

<script>
import CFileUpload from "./FileUpload.vue";
import CGallery from "../Gallery.vue";
export default {
  name: "CGallerySection",
  props: ["apartment"],
  data: function () {
    return {};
  },
  components: {
    CGallery,
    CFileUpload,
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
