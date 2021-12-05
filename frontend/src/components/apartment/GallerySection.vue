<template>
  <div>
     <c-file-upload :apartmentId="apartment.id"></c-file-upload>
  </div>
</template>

<script>
import CFileUpload from "./FileUpload.vue"
export default {
  name: "CGallerySection",
  components: {
     CFileUpload,
  },
  props: ["apartment"],
  data: function () {
    return {
     
    };
  },
  computed: {},
  mounted() {},
  methods: {
    
  },
};
</script>
