<template>
  <div>
    <v-container class="d-flex justify-center pb-10">
      <v-card elevation="2" class="col-12 col-md-8 pb-md-10">
        <v-card-title class="text-h3 zabatana corange--text px-0 pa-md-4"
          >Képek feltöltése</v-card-title
        >
        <v-card-text class="pa-0 pa-md-4">
          <vue-dropzone
            ref="myVueDropzone"
            id="dropzone"
            :options="dropzoneOptions"
            v-on:vdropzone-file-added="fileAdded"
            v-on:vdropzone-removed-file="fileRemoved"
          ></vue-dropzone>
        </v-card-text>
        <v-card-actions class="pa-0 pa-md-4">
          <v-btn
            elevation="2"
            color="cgreen"
            class="white--text mt-5 "
            @click="savePictures"
            >FELTÖLTÉS
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-container>
  </div>
</template>

<script>
import vue2Dropzone from "vue2-dropzone";
import "vue2-dropzone/dist/vue2Dropzone.min.css";
import ApiService from "../../services/api.service"

export default {
  name: "CFileUpload",
  components: {
    vueDropzone: vue2Dropzone,
  },
  props: ["apartmentId"],
  data: function () {
    return {
      dropzoneOptions: {
        url: "https://httpbin.org/post",
        uploadMultiple: true,
        autoProcessQueue: false,
        addRemoveLinks: true,
        acceptedFiles: "image/*",
        dictDefaultMessage: "Húzd ide a feltöltésre szánt képeket",
      },
      filesToUpload: [],
    };
  },
  computed: {},
  mounted() {},
  methods: {
    fileAdded(file) {
      this.$refs.myVueDropzone.processQueue();
      this.filesToUpload.push(file);
    },
    fileRemoved(file) {
      this.filesToUpload = this.filesToUpload.filter(deleteFile);
      function deleteFile(f) {
        return JSON.stringify(f) !== JSON.stringify(file);
      }
    },
    savePictures() {
      const formData = new FormData();
      for (let file of this.filesToUpload) {
        formData.append("files", file);
      }
      formData.append("apartmentId", this.apartmentId);
      ApiService.POST( "apartment/addpictures", formData)
        .then((response) => {
          this.$emit("uploaded-pictures", response.data);
          this.$store.commit("showMessage", {
            active: true,
            color: "cgreen",
            message: "Sikeres feltöltés",
          });
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "Nem sikerült feltölteni a képeket. Próbáld újra!",
          });
        });
      this.$refs.myVueDropzone.removeAllFiles(true);
    },
  },
};
</script>
<style lang="scss">
#dropzone {
  outline: 2px dashed grey; /* the dash box */
  outline-offset: -10px;
  background: lightcyan;
  color: dimgray;
  padding: 10px 10px;
  min-height: 200px; /* minimum height */
  position: relative;
}

#dropzone:hover {
  background: lightblue; /* when mouse over to the drop zone, change color */
}
</style>
