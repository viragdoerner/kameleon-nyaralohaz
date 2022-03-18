<template>
  <v-container class="col-12 mx-0 reserve-section" fluid>
    <v-container class=" d-flex flex-column flex-md-row px-md-16 px-2 col-12 col-sm-9 col-md-10" fluid>
      <v-card  class="pa-0 pa-md-10 ma-md-10 ms-1 transparentCard col-md-6 col-12" elevation="0">
        <v-card-title class="text-h2 zabatana cgreen--text"
          >Foglalj apartmant</v-card-title
        >
        <v-card-text>
          <c-rich-text-editor
            :description="description"
            v-on:update-description="onUpdateDescription"
          ></c-rich-text-editor>
        </v-card-text>
      </v-card>
      <v-card  class="pa-md-10 pa-4 ma-md-10  ma-0 mt-5 mt-md-10 col-12 col-sm-8 col-md-6 align-self-center ">
        <v-card-text>
          <v-row>
            <v-text-field
              v-model="dateRangeText"
              label="Date range"
              prepend-icon="mdi-calendar"
              readonly
            ></v-text-field>
          </v-row>
          <v-row>
            <v-combobox filled solo></v-combobox>
          </v-row>
          <v-row>
            <v-combobox filled solo></v-combobox>
          </v-row>
          <v-row>
            <v-btn elevation="2" x-large color="corange" class="white--text"
              >FOGLALÁS
            </v-btn>
          </v-row>
        </v-card-text>
      </v-card>
    </v-container>
  </v-container>
</template>

<script>
import CRichTextEditor from "./RichTextEditor.vue";
export default {
  name: "CReserveSection",
  data: () => ({
    dateRangeText: null
  }),
  methods: {
    onUpdateDescription(newDescription) {
      this.$emit("update-description", newDescription);
    },
  },
  components: { CRichTextEditor },
  props: ["description"],
};
</script>
<style  scoped>
.reserve-section {
  background-color: white;
}
</style>
