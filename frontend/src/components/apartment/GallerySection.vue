<template>
  <div class="py-16 gallery-section">
    <c-file-upload
      v-if="$store.state.admin"
      :apartmentId="apartment.id"
      v-on:uploaded-pictures="onUploadedPictures"
    ></c-file-upload>
    <v-container class="col-10">
      <c-gallery
        :pictures="apartment.pictures"
        v-on:delete-picture="onDeletePicture"
      ></c-gallery>
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
    onDeletePicture(pic){
      
    }
  },
};
</script>

<style  scoped>
.gallery-section {
  background-color: white;
}
</style>
