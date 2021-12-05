<template>
  <div>
    <c-file-upload
      v-if="$store.state.admin"
      :apartmentId="apartment.id"
      v-on:uploaded-pictures="onUploadedPictures"
    ></c-file-upload>
  </div>
</template>

<script>
import CFileUpload from "./FileUpload.vue";
export default {
  name: "CGallerySection",
  components: {
    CFileUpload,
  },
  props: ["apartment"],
  data: function () {
    return {};
  },
  computed: {},
  mounted() {},
  methods: {
    onUploadedPictures(updatedApartment){
      this.apartment = updatedApartment;
    }
  },
};
</script>
