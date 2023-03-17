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
        <v-progress-linear
          v-if="loading"
          :indeterminate="loading"
          absolute
          bottom
          color="cgreen"
        ></v-progress-linear>
        <v-card-actions class="pa-0 pa-md-4">
          <v-btn
            elevation="2"
            color="cgreen"
            class="white--text mt-5"
            @click="savePictures"
            :disabled="filesToUpload.length==0"
            >FELTÖLTÉS
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-container>
  </div>
</template>

<script>
import vue2Dropzone from "vue2-dropzone";
import CloudinaryService from "../../services/cloudinary.service";
import "vue2-dropzone/dist/vue2Dropzone.min.css";

export default {
  name: "CFileUpload",
  components: {
    vueDropzone: vue2Dropzone,
  },
  props: ["apartmentId"],
  data() {
    return {
      loading: false,
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
      //this.$refs.myVueDropzone.processQueue();
      this.filesToUpload.push(file);
    },
    fileRemoved(file) {
      this.filesToUpload = this.filesToUpload.filter(deleteFile);
      function deleteFile(f) {
        return JSON.stringify(f) !== JSON.stringify(file);
      }
    },
    savePictures() {
      this.loading = true;
      CloudinaryService.saveImages(this.filesToUpload, this.apartmentId)
        .then((response) => {
          this.$emit("uploaded-pictures", response.data);
          this.$store.commit("showMessage", {
            active: true,
            color: "cgreen",
            message: "Sikeres képfeltöltés",
          });
          this.loading = false;
          this.$refs.myVueDropzone.removeAllFiles(true);
        })
        .catch((error) => {
          this.loading = false;
          console.log(error);
          this.$refs.myVueDropzone.removeAllFiles(true);
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "Nem sikerült feltölteni a képeket. Próbáld újra!",
          });
        });
    },
  },
};
</script>
<style lang="scss">
#dropzone .dz-progress {
  display: none;
}
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
