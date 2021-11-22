<template>
  <v-container class="col-12 mx-0 reserve-section" fluid>
    <v-container class="d-flex px-16 col-10" fluid>
      <v-card width="50%" class="pa-10 ma-10 transparentCard" elevation="0">
        <v-card-title class="text-h3 zabatana cgreen--text"
          >Foglalj apartmant</v-card-title
        >
        <v-card-text>
          <c-rich-text-editor
            :description="description"
            v-on:update-description="onUpdateDescription"
          ></c-rich-text-editor>
        </v-card-text>
      </v-card>
      <v-card width="50%" class="pa-10 ma-10">
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
    dateRangeText: null,
    color: "#206a5d",
    font: "Roboto",
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
