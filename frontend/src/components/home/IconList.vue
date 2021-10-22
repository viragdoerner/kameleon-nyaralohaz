<template>
  <v-container class="d-flex justify-space-around px-16 icons-container" fluid>
    <div
      v-for="(p,index) in properties"
      :key="p.id"
      class="pa-2 d-flex flex-column align-center my-16"
    >
      <div class="align-self-center">
        <v-icon x-large size="24px" v-bind:style="{ color: colors[index%5] }" > {{p.icon_name}} </v-icon>
      </div>
      <div class="align-self-center cgreen--text">{{ p.name }}</div>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "CIconList",
  props: ["properties"],
  components: {},
  data: () => ({
    colors: ["darkblue","orange","purple",  "green",  "darkred"],
  })
};
</script>
<style scoped>
.icons-container {
  background-color: #d4ecdd;
}
</style>
