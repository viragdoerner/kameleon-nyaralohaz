<template>
  <div class="py-md-16 py-8 gallery-section">
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
import ApiService from "../../services/api.service";
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
      this.$emit("updateApartment", updatedApartment);
      //this.apartment = updatedApartment;
    },
    onDeletePicture(url) {
      const formData = new FormData();
      formData.append("imageURL", url);
      formData.append("apartmentId", this.apartment.id);
      console.log(FormData);
      ApiService.POST("apartment/deletepicture", formData)
        .then((response) => {
          this.$emit("updateApartment", response.data);
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
