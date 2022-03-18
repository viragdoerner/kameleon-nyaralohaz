<template>
  <div>
    <!-- <v-parallax src="../../assets/bg.png">
    <v-row align="center" class="pa-16 ma-16">
      <v-col cols="7">
        <img src="../../assets/cim_feher.svg" />
        <h3 class="corange--text">BALATONSZEMES</h3>
      </v-col>
    </v-row>
  </v-parallax> -->
    <v-parallax src="../../assets/balatonszemes.jpg" :height="innerHeight">
      <v-row align="start" justify="start" class="pa-md-16 pa-0 ma-md-16 mx-0 mt-6 d-flex">
        <v-col class="col-10 col-md-7 mt-0 mt-sm-14 pt-16 mt-md-0  pt-sm-0 ">
          <img class="d-sm-none" src="../../assets/mobil_cim_logo.svg" />
           <img class="d-none d-sm-block" src="../../assets/cim_feher.svg" />
          <h2 class="corange--text">BALATONSZEMES</h2>
        </v-col>
        <div>
  </div>
      </v-row>
      
    </v-parallax>
  </div>
</template>

<script>
export default {
  name: "CHeader",

  components: {},
  data: () => ({}),
  computed: {
    innerHeight: function () {
      return window.innerHeight;
    },
  },
};
</script>
<style scoped>
.header-title-container {
  opacity: 0.4;
}
</style>