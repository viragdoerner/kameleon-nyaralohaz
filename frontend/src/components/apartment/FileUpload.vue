<template>
  <div>
    <v-container>
      <vue-dropzone
        ref="myVueDropzone"
        id="dropzone"
        :options="dropzoneOptions"
        v-on:vdropzone-file-added="fileAdded"
        v-on:vdropzone-removed-file="fileRemoved"
      ></vue-dropzone>
      <v-row class="d-flex justify-center pa-3">
          <v-btn
            elevation="2"
            color="cgreen"
            class="white--text mt-5"
            @click="savePictures"
            >FELTÖLTÉS
          </v-btn>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import vue2Dropzone from "vue2-dropzone";
import "vue2-dropzone/dist/vue2Dropzone.min.css";
import axios from "axios";

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
        acceptedFiles: "image/*"
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
      axios
        .post(this.$store.state.baseURL + "apartment/addpictures", formData)
        .then((response) => {
          if (!response.data) throw "empty list";
        })
        .catch((error) => {
          alert(error);
        });
        this.$refs.myVueDropzone.removeAllFiles()
    },
  },
};
</script>
