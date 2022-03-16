<template>
  <div class="py-16 gallery-section">
    <c-file-upload
      v-if="$store.getters.loggedIn && $store.getters.isAdmin"
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
import ApiService from "../../services/api.service"
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
    onDeletePicture(pic) {
      console.log(pic);
      ApiService.DELETE( "apartment/deletepic/" + this.apartment.id + "/" + pic)
        .then((response) => {
          this.apartment = response.data;
          this.$store.commit("showMessage", {
            active: true,
            color: "cgreen",
            message: "Sikeres törlés",
          });
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "HIba történt a kép törlésekor. Próbáld újra!",
          });
        });
    },
  },
};
</script>

<style  scoped>
.gallery-section {
  background-color: white;
}
</style>
