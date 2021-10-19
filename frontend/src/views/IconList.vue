<template>
  <v-container class="d-flex justify-space-around px-16 icons-container" fluid>
    <div
      v-for="n in 5"
      :key="n"
      class="pa-2 d-flex flex-column align-center my-16"
    >
      <div class="align-self-center">
        <v-icon x-large size="24px" color="purple darken-3"> fa-paw </v-icon>
      </div>
      <div class="align-self-center primary--text">szöveg</div>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "CIconList",

  components: {},
};
</script>
<style scoped>
.icons-container {
  background-color: #d4ecdd;
}
</style>
