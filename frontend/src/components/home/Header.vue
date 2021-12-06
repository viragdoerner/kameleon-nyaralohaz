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
    <v-row align="top" class="pa-16 ma-16 mt-10">
      <v-col cols="7">
        <img src="../../assets/cim_feher.svg" />
        <h2 class="corange--text">BALATONSZEMES</h2>
      </v-col>
    </v-row>
  </v-parallax>
</div>
</template>

<script>
export default {
  name: "CHeader",

  components: {},
  data: () => ({}),
  computed:{
    innerHeight: function(){
      return window.innerHeight;
    }
  }
};
</script>
<style scoped>
.header-title-container {
  opacity: 0.4;
}
</style>