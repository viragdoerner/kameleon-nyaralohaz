<template>
  <v-container class="d-flex justify-space-around px-16 icons-container" fluid>
    <v-icon large
      v-for="n in 5"
      :key="n"
      class="pa-2"
      size="24px"
      color="purple darken-3"
    >
      fa-paw
    </v-icon>
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
